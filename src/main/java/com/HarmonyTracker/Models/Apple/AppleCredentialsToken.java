package com.HarmonyTracker.Models.Apple;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AppleCredentialsToken {

    private String identityToken;
    private String state;
    private String email;
    private String authorizationCode;
    private AppleName fullName;
    private int realUserStatus;
    private String user;


}



