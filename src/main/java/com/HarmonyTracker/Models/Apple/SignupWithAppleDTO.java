package com.HarmonyTracker.Models.Apple;

import com.HarmonyTracker.Entities.Macros;
import DTO.BodyDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupWithAppleDTO {
    AppleCredentialsToken credentialsToken;
    BodyDetailsDTO bodyDetails;
    Macros macros;
}
