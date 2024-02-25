package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.Entities.Enums.BmiClassificationType;
import com.HarmonyTracker.Entities.Enums.GenderType;
import com.HarmonyTracker.Models.Preview.BodyDetails;
import com.HarmonyTracker.Models.Preview.BodyDetailsDTO;
import com.HarmonyTracker.Models.Preview.DemoMacro;
import com.HarmonyTracker.Utils.CalculatorUtil;
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
    public final CalculatorUtil calculatorUtil=new CalculatorUtil();
    public DemoMacro createMacroPlan(BodyDetails bodyDetails) {
        var bmi = bodyDetails.getWeight() / Math.pow(bodyDetails.getHeight() * 0.01, 2);
        var macros = DemoMacro.builder()
                .bmi(bmi)
                .bmiClassificationType(BmiClassificationType.getType(bmi))
                .build();
        if(bodyDetails.getGenderType().equals(GenderType.male)){
            macros.setBmr(10 * bodyDetails.getWeight() + 6.25 * bodyDetails.getHeight() - 5 * (Period.between(bodyDetails.getBirthDate(), LocalDate.now())).getYears()+5);
        }else if(bodyDetails.getGenderType().equals(GenderType.female)){
            macros.setBmr(10 * bodyDetails.getWeight() + 6.25 * bodyDetails.getHeight() - 5 * (Period.between(bodyDetails.getBirthDate(), LocalDate.now())).getYears()-161);
        }

        if( bodyDetails.getExtraData()!=null){
            if(bodyDetails.getGenderType().equals(GenderType.male)) {
               macros.setBodyFatPercentage(calculatorUtil.MaleBodyFatPercentage(
                       bodyDetails.getExtraData().getWaistNavel(),
                       bodyDetails.getHeight(),
                       bodyDetails.getExtraData().getNeckNarrowest()
               ));
            }
            else{
                macros.setBodyFatPercentage(calculatorUtil.FemaleBodyFatPercentage(
                        bodyDetails.getExtraData().getWaistNavel(),
                        bodyDetails.getHeight(),
                        bodyDetails.getExtraData().getNeckNarrowest(),
                        bodyDetails.getExtraData().getHipWidest()
                ));
            }
        }

        return macros;
    }
}

