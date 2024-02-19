package com.HarmonyTracker.CSR.Controllers;

import com.HarmonyTracker.CSR.Repositories.TokenRepository;
import com.HarmonyTracker.CSR.Repositories.UserRepository;
import com.HarmonyTracker.CSR.Services.AuthenticationServices;
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
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
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

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Collections;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenRepository tokenRepository;
    private final AuthenticationServices authenticationServices;

    @PostMapping("/1")
    public ResponseEntity<?> googleAuthentication(@RequestBody String token) {
        try{
            var response=authenticationServices.googleAuthentication(token);
            return ResponseEntity.ok(response);
        }catch (InternalError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
    @PostMapping("/2")
    public ResponseEntity<?> facebookAuthentication(@RequestBody FBAccessTokenJSON fbAccessTokenJSON) {
        try{
            var response= authenticationServices.facebookAuthentication(fbAccessTokenJSON);
            return ResponseEntity.ok(response);
        }catch (InternalError e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/3")
    public ResponseEntity<?> appleAuthentication(@RequestBody AppleCredentialsToken credentialsToken) {
        try{
            var response= authenticationServices.appleAuthentication(credentialsToken);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (InternalError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/email")
    public ResponseEntity<AuthenticationResponse> emailRegistration(@RequestBody RegisterRequest request){
        try{
            var response=authenticationServices.emailRegistration(request);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/emailLogin")
    public ResponseEntity<AuthenticationResponse> emailAuthentication(@RequestBody AuthenticationRequest request){
        try{
            var response=authenticationServices.emailAuthentication(request);
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
