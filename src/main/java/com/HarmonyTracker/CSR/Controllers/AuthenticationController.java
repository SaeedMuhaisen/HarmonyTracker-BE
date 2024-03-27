package com.HarmonyTracker.CSR.Controllers;

import com.HarmonyTracker.CSR.Services.AuthenticationServices;
import com.HarmonyTracker.Entities.BodyDetails;
import com.HarmonyTracker.Mappers.BodyDetailsMapper;
import com.HarmonyTracker.Models.Apple.AppleCredentialsToken;
import com.HarmonyTracker.Models.Apple.SignupWithAppleDTO;
import com.HarmonyTracker.Models.Authentication.AuthenticationRequest;
import com.HarmonyTracker.Models.Authentication.AuthenticationResponse;
import com.HarmonyTracker.Models.Authentication.RegisterRequest;
import com.HarmonyTracker.Models.Facebook.FBAccessTokenJSON;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServices authenticationServices;

    @PostMapping("/1")
    public ResponseEntity<?> googleAuthentication(@RequestBody String token) {
        try{
            var response=authenticationServices.googleAuthentication(token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    public ResponseEntity<?> appleAuthentication(@RequestBody SignupWithAppleDTO signupWithAppleDTO) {
        try{
            var response= authenticationServices.appleAuthentication(signupWithAppleDTO.getCredentialsToken(),signupWithAppleDTO.getBodyDetails(),signupWithAppleDTO.getMacros());
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
