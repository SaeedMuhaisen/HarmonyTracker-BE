package com.HarmonyTracker.Models.Preview;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Macros {
    private double bmr;
    private double tdee;
    private double carbs;
    private double fat;
    private double protein;
}
