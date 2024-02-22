package com.HarmonyTracker.Models.Preview;

import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.BmiClassificationType;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoMacro {
    private double bmr;
    private ActivityLevel activityLevel;
    private double bmi;
    private BmiClassificationType bmiClassificationType;
    private double bodyFatPercentage;
    private double dietType;
    private double tdee;
}
