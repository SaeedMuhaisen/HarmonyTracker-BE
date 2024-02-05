package com.HarmonyTracker.CSR.Controllers;

import com.HarmonyTracker.CSR.Repositories.TokenRepository;
import com.HarmonyTracker.CSR.Repositories.UserRepository;
import com.HarmonyTracker.CSR.Services.JwtService;
import com.HarmonyTracker.Entities.Enums.AuthType;
import com.HarmonyTracker.Entities.Enums.TokenType;
import com.HarmonyTracker.Entities.Token;
import com.HarmonyTracker.Entities.User;
import com.HarmonyTracker.Models.Apple.AppleCredentialsToken;
import com.HarmonyTracker.Models.Authentication.AuthenticationRequest;
import com.HarmonyTracker.Models.Authentication.AuthenticationResponse;
import com.HarmonyTracker.Models.Authentication.RegisterRequest;
import com.HarmonyTracker.Models.Facebook.FBAccessTokenJSON;
import com.HarmonyTracker.Models.Facebook.FacebookUserModel;
import com.HarmonyTracker.Models.Facebook.FacebookValidationModel;
import com.HarmonyTracker.Utils.AppleLoginUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Collections;

@Controller
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class AuthenticationController {

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

    public static final String FACEBOOK_AUTH_URL = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=%s";

    private final WebClient webClient;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    @PostMapping("/1")
    public ResponseEntity<?> googleAuthentication(@RequestBody String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier
                        .Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(clientId))
                        .build();
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload

            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            var user = User.builder()
                    .firstname(givenName)
                    .lastname(familyName)
                    .email(email)
                    .oauthId(payload.getSubject())
                    .authType(AuthType.Google)
                    .build();
            var savedUser= userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build());
        }
        return null;

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
    @PostMapping("/2")
    public ResponseEntity<?> facebookAuthentication(@RequestBody FBAccessTokenJSON fbAccessTokenJSON) {
        FacebookValidationModel response;
        try {
            response = webClient.get()
                    .uri(String.format(fbAuthUrl, fbAccessTokenJSON.getAccessToken(), fbAppId, fbClientSecret))
                    .retrieve()
                    .bodyToMono(FacebookValidationModel.class)
                    .block();
            int i=0;
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing Facebook validation");
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
                System.out.println(response);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            else {
                //check first do we have such user in our database?
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
                    System.out.println(e);
                }


                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build());
            }

    }
    @PostMapping("/3")
    public ResponseEntity<?> appleAuthentication(@RequestBody AppleCredentialsToken credentialsToken) throws Exception {
        try{
            var result= appleLoginUtil.appleAuth(credentialsToken.getAuthorizationCode());
//todo: email verified? should this always be true?
            if(result==null
                    || !result.getIss().equals(appleIss)
                    || !result.getAud().equals(appleClientId)
                    || result.getAuth_time()+ 32000<= Instant.now().getEpochSecond()
                    || result.getExp()<Instant.now().getEpochSecond()
                    || result.getEmail()==null
            ){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            else {
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
                            .build();
                    user = userRepository.save(userToSave);
                } else {
                    user = userExists.get();
                }

                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                saveUserToken(user, jwtToken);

                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build());
            }

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to validate token");
        }
    }
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/email")
    public ResponseEntity<AuthenticationResponse> emailRegistration(@RequestBody RegisterRequest request){

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
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }
    private final AuthenticationManager authenticationManager;
    @PostMapping("/emailLogin")
    public ResponseEntity<AuthenticationResponse> emailAuthentication(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }
}
