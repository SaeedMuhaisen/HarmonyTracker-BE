package com.HarmonyTracker.Models.Authentication;

import DTO.BodyDetailsDTO;
import com.HarmonyTracker.Entities.Macros;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private BodyDetailsDTO bodyDetails;
    private Macros macros;
}
