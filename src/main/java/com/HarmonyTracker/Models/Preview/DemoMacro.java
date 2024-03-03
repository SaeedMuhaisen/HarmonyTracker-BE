package com.HarmonyTracker.Models.Preview;

import com.HarmonyTracker.Entities.Enums.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoMacro {
    private double bmi;
    private BmiClassificationType bmiClassificationType;

    private double bodyFatPercentage;
    private BFPType bfpType; //this tells us based on what we calculated BodyFatPercentage

    private double bodyFatMass;
    private BodyFatMassClassificationType bodyFatMassClassificationType;
    private double leanBodyMass;

    private double bmr;
    private BMRType bmrType;

}
