package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.Entities.Enums.BmiClassificationType;
import com.HarmonyTracker.Models.Preview.BodyDetails;
import com.HarmonyTracker.Models.Preview.BodyDetailsDTO;
import com.HarmonyTracker.Models.Preview.DemoMacro;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class PreviewServices {

    public DemoMacro createMacroPlan(BodyDetails bodyDetails) {
        var bmi=bodyDetails.getHeight() / (bodyDetails.getWeight()* bodyDetails.getWeight());
        var macros=DemoMacro.builder()
                .bmr(10* bodyDetails.getWeight()+6.25* bodyDetails.getHeight()-5 * (Period.between(bodyDetails.getBirthDate(),LocalDate.now())).getYears())
                .bmi(bmi)
                .bmiClassificationType(BmiClassificationType.getType(bmi))
                .build();

        return null;
    }
}
