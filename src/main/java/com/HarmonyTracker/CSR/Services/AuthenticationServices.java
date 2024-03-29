package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.CSR.Repositories.TokenRepository;
import com.HarmonyTracker.CSR.Repositories.UserRepository;
import com.HarmonyTracker.Entities.BodyDetails;
import com.HarmonyTracker.Entities.Enums.AuthType;
import com.HarmonyTracker.Entities.Enums.TokenType;
import com.HarmonyTracker.Entities.Macros;
import com.HarmonyTracker.Entities.Token;
import com.HarmonyTracker.Entities.User;
import com.HarmonyTracker.Mappers.BodyDetailsMapper;
import com.HarmonyTracker.Models.Apple.AppleCredentialsToken;
import com.HarmonyTracker.Models.Apple.IdTokenPayload;
import com.HarmonyTracker.Models.Authentication.AuthenticationRequest;
import com.HarmonyTracker.Models.Authentication.AuthenticationResponse;
import com.HarmonyTracker.Models.Authentication.RegisterRequest;
import com.HarmonyTracker.Models.Facebook.FBAccessTokenJSON;
import com.HarmonyTracker.Models.Facebook.FacebookUserModel;
import com.HarmonyTracker.Models.Facebook.FacebookValidationModel;
import DTO.BodyDetailsDTO;
import com.HarmonyTracker.Utils.AppleLoginUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Collections;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class AuthenticationServices {

    @Value("${Google-Client-Id}")
    private String clientId;
    @Value("${fb-app-id}")
    private String fbAppId;
    @Value("${fb-client-secret}")
    private String fbClientSecret;
    @Value("${fb-auth-url}")
    private String fbAuthUrl;
    @Value("${fb-user-details}")
    private String fbUserDetails;
    @Value("${apple-iss}")
    private String appleIss;
    @Value("${apple-client-id}")
    private String appleClientId;
    private final AppleLoginUtil appleLoginUtil;

    private final WebClient webClient;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse googleAuthentication(@RequestBody String token) throws InternalError, AuthenticationException, GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier
                        .Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(clientId))
                        .build();

           GoogleIdToken idToken = verifier.verify(token);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            User user;
            var userExists = userRepository.findByOauthId(payload.getSubject());

            user = userExists.orElseGet(() -> User.builder()
                    .firstname((String) payload.get("given_name"))
                    .lastname((String) payload.get("family_name"))
                    .email(payload.getEmail())
                    .oauthId(payload.getSubject())
                    .authType(AuthType.Google)
                    .build());

            var savedUser= userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        else {
            throw new AuthenticationException("id Token is null");
        }
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse facebookAuthentication(FBAccessTokenJSON fbAccessTokenJSON) throws AuthenticationException,InternalError {
        FacebookValidationModel response;
        try {
            response = webClient.get()
                    .uri(String.format(fbAuthUrl, fbAccessTokenJSON.getAccessToken(), fbAppId, fbClientSecret))
                    .retrieve()
                    .bodyToMono(FacebookValidationModel.class)
                    .block();
        } catch (Exception e) {
            throw new InternalError("Error retrieving from WebClient");
        }
        //we check first if everythign is correct and the graph is confirming the access token
        if(response.getData()==null ||
                response.getData().getError()!=null ||
                !response.getData().isValid() ||
                response.getData().getExpiresAt()<= Instant.now().getEpochSecond() ||
                response.getData().getIssuedAt() + 32000<= Instant.now().getEpochSecond() ||
                !response.getData().getAppId().equals(fbAccessTokenJSON.getApplicationID()) ||
                !response.getData().getUserId().equals(fbAccessTokenJSON.getUserID()) ||
                fbAccessTokenJSON.getExpirationTime() <= Instant.now().getEpochSecond()
        ){

            throw new AuthenticationException("Invalid fbAccessToken");
        }
        else {
            User user;
            var userExists = userRepository.findByOauthId(response.getData().getUserId());
            if (!userExists.isPresent()) {
                // Call the graph and collect basic information about the user:
                FacebookUserModel facebookUserModel = webClient.get()
                        .uri(fbUserDetails)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + fbAccessTokenJSON.getAccessToken())
                        .retrieve()
                        .bodyToMono(FacebookUserModel.class)
                        .block();

                var userToSave = User
                        .builder()
                        .oauthId(response.getData().getUserId())
                        .authType(AuthType.Facebook)
                        .firstname(facebookUserModel.getFirstName())
                        .lastname(facebookUserModel.getLastName())
                        .email(facebookUserModel.getEmail() != null ? facebookUserModel.getEmail() : "NULL")
                        .build();

                user = userRepository.save(userToSave);
            } else {
                user = userExists.get();
            }

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);
            try{
                String res= webClient.delete()
                        .uri(String.format("https://graph.facebook.com/v19.0/me/permissions?access_token=%s", fbAccessTokenJSON.getAccessToken()))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            }catch (Exception e){
                throw new InternalError("Error retrieving from WebClient");
            }
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)

                    .build();
        }

    }

    public AuthenticationResponse appleAuthenticationSignUp(AppleCredentialsToken credentialsToken, BodyDetailsDTO bodyDetailsDTO, Macros macros) throws AuthenticationException, InternalError, InstanceAlreadyExistsException {
        IdTokenPayload result;
        BodyDetails bodyDetails = BodyDetailsMapper.INSTANCE.toEntity(bodyDetailsDTO);

        try {
                 result = appleLoginUtil.appleAuth(credentialsToken.getAuthorizationCode());
            }
            catch (Exception e){
                    throw new InternalError("internal Error in getAuthorizationCode() method - AppleAuthentication");
            }
            if(result!=null
                    && result.getIss().equals(appleIss)
                    && result.getAud().equals(appleClientId)
                    && result.getAuth_time()+ 32000> Instant.now().getEpochSecond()
                    && result.getExp()>=Instant.now().getEpochSecond()
                    && result.getEmail()!=null
            ){
                //check first do we have such user in our database?
                User user;
                var userExists = userRepository.findByOauthId(result.getSub());
                if (!userExists.isPresent()) {
                    // Call the graph and collect basic information about the user:

                    var userToSave = User
                            .builder()
                            .oauthId(result.getSub())
                            .authType(AuthType.Apple)
                            .firstname(credentialsToken.getFullName().getGivenName())
                            .lastname(credentialsToken.getFullName().getFamilyName())
                            .email(result.getEmail())
                            .bodyDetails(bodyDetails)
                            .macros(macros)
                            .build();
                    user = userRepository.save(userToSave);
                } else {
                    throw new InstanceAlreadyExistsException("User Already Exists");
                }

                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                saveUserToken(user, jwtToken);

                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .bodyDetails(BodyDetailsMapper.INSTANCE.toDTO(user.getBodyDetails()))
                        .macros(user.getMacros())
                        .build();
            }
            else{
                throw new AuthenticationException("result authentication is null");
            }
    }


    public AuthenticationResponse emailRegistration(RegisterRequest request)throws AuthenticationException{
        var found=userRepository.findByEmail(request.getEmail());
        if(found.isPresent())
            throw new AuthenticationException("Email already registered");

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .authType(AuthType.Email)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }



    public AuthenticationResponse emailAuthentication(AuthenticationRequest request) throws AuthenticationException, NoSuchElementException {

        var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse appleAuthenticationLogin(AppleCredentialsToken appleCredentialsToken) throws AuthenticationException,NoSuchElementException {
        IdTokenPayload result;

        try {
            result = appleLoginUtil.appleAuth(appleCredentialsToken.getAuthorizationCode());
        } catch (Exception e) {
            throw new InternalError("internal Error in getAuthorizationCode() method - AppleAuthentication");
        }
        if (result != null
                && result.getIss().equals(appleIss)
                && result.getAud().equals(appleClientId)
                && result.getAuth_time() + 32000 > Instant.now().getEpochSecond()
                && result.getExp() >= Instant.now().getEpochSecond()
                && result.getEmail() != null
        ) {
            //check first do we have such user in our database?
            User user;
            var userExists = userRepository.findByOauthId(result.getSub());
            if (!userExists.isPresent()) {
                throw new NoSuchElementException("User Not Found");
            } else {
                user = userExists.get();
            }

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .bodyDetails(BodyDetailsMapper.INSTANCE.toDTO(user.getBodyDetails()))
                    .macros(user.getMacros())
                    .build();
        } else {
            throw new AuthenticationException("result authentication is null");
        }
    }
}
