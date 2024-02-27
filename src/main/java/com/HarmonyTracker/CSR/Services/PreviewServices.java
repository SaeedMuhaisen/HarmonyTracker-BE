package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.Entities.Enums.BmiClassificationType;
import com.HarmonyTracker.Entities.Enums.GenderType;
import com.HarmonyTracker.Models.Preview.BodyDetails;
import com.HarmonyTracker.Models.Preview.DemoMacro;
import com.HarmonyTracker.Models.Preview.Macros;
import com.HarmonyTracker.Utils.CalculatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
@Component
public class PreviewServices {
    public final CalculatorUtil calculatorUtil=new CalculatorUtil();
    public DemoMacro createMacroPlan(BodyDetails bodyDetails) {
        var macros = new DemoMacro();
        var bmi = bodyDetails.getWeight() / Math.pow(bodyDetails.getHeight() * 0.01, 2);
        var age=Period.between(bodyDetails.getBirthDate(), LocalDate.now()).getYears();

        macros.setBmi(bmi);
        macros.setBmiClassificationType(BmiClassificationType.getType(bmi));

        if(bodyDetails.getGenderType().equals(GenderType.male)) {

            macros.setBmrMSJ(10 * bodyDetails.getWeight() + 6.25 * bodyDetails.getHeight() - 5 * age + 5);
            macros.setBmrRHE(13.397 * bodyDetails.getWeight() + 4.799 * bodyDetails.getHeight() - 5.677 * age + 88.362);
            if (bodyDetails.getExtraData() != null) {
                macros.setBodyFatPercentage(calculatorUtil.MaleBodyFatPercentage(
                        bodyDetails.getExtraData().getWaistNavel(),
                        bodyDetails.getHeight(),
                        bodyDetails.getExtraData().getNeckNarrowest()));
            }
            else{
                macros.setBodyFatPercentage(calculatorUtil.MaleBodyFatPercentage(bmi,age));
            }
        }
        else if(bodyDetails.getGenderType().equals(GenderType.female)) {
            macros.setBmrMSJ(10 * bodyDetails.getWeight() + 6.25 * bodyDetails.getHeight() - 5 * age - 161);
            macros.setBmrRHE(9.247 * bodyDetails.getWeight() + 3.098 * bodyDetails.getHeight() - 4.330 * age + 447.593);
            if (bodyDetails.getExtraData() != null) {
                macros.setBodyFatPercentage(calculatorUtil.FemaleBodyFatPercentage(
                        bodyDetails.getExtraData().getWaistNavel(),
                        bodyDetails.getHeight(),
                        bodyDetails.getExtraData().getNeckNarrowest(),
                        bodyDetails.getExtraData().getHipWidest()
                ));
            }
            else{
                macros.setBodyFatPercentage(calculatorUtil.FemaleBodyFatPercentage(bmi,age));
            }

        }
        macros.setBmrKMA(370+21.6*(1-macros.getBodyFatPercentage()/100)*bodyDetails.getWeight());
        macros.setLeanBodyMass(bodyDetails.getWeight()-macros.getBodyFatPercentage()/100*bodyDetails.getWeight());
        macros.setBodyFatMass(bodyDetails.getWeight()*macros.getBodyFatPercentage()/100);
        return macros;
    }
}

