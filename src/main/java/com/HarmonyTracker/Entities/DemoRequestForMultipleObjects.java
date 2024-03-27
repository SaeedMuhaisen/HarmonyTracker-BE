package com.HarmonyTracker.Entities;

import DTO.BodyDetailsDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoRequestForMultipleObjects {
    BodyDetailsDTO bodyDetailsDTO;
    BodyDetailsDTO s;
}
