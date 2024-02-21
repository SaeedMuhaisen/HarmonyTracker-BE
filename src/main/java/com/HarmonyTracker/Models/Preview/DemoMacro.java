package com.HarmonyTracker.Models.Preview;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoMacro {
    private double bmi;
    private double bodyFatPercentage;
    private double dietType;
    private double tdee;
}
