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
    private double bmi;
    private double bodyFatPercentage;
    private BmiClassificationType bmiClassificationType;

    private double bmrMSJ;
    private double bmrRHE;
    private double bmrKMA;

    private double tdeeRHE;
    private double tdeeMSJ;
    private double tdeeKMA;

    private double dietType;

    private Macros MacrosMSJ;
    private Macros MacrosRHE;
    private Macros MacrosKMA;

}
