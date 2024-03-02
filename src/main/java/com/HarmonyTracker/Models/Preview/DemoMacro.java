package com.HarmonyTracker.Models.Preview;

import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.BmiClassificationType;
import com.HarmonyTracker.Entities.Enums.BodyFatMassClassificationType;
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
    private BmiClassificationType bmiClassificationType;
    private double bodyFatMass;
    private BodyFatMassClassificationType bodyFatMassClassificationType;
    private double leanBodyMass;
    private double bmrMSJ;
    private double bmrRHE;
    private double bmrKMA;
}
