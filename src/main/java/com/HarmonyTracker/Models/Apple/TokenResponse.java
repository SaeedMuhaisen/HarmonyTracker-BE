package com.HarmonyTracker.Models.Apple;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
    public class TokenResponse {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String id_token;


}


