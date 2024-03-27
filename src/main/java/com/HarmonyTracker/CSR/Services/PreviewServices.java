package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.Entities.Enums.*;
import com.HarmonyTracker.Entities.BodyDetails;
import com.HarmonyTracker.Models.Preview.DemoMacro;
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
//        var macros = new DemoMacro();
//
//        var bmi = calculatorUtil.calculateBMI(bodyDetails.getWeight(),bodyDetails.getHeight());
//        var age=Period.between(bodyDetails.getBirthDate(), LocalDate.now()).getYears();
//
//        macros.setBmi(bmi);
//        macros.setBmiClassificationType(BmiClassificationType.getType(bmi));
//
//        if (bodyDetails.isExtraData()) {
//            macros.setBodyFatPercentage(calculatorUtil.calculateBFPWithUnits(
//                    bodyDetails.getWaistNavel(),
//                    bodyDetails.getHeight(),
//                    bodyDetails.getNeckNarrowest(),
//                    bodyDetails.getHipWidest(),
//                    bodyDetails.getGenderType()
//            ));
//            macros.setBfpType(BFPType.ExtraData);
//            macros.setBmr(370+21.6*(1-macros.getBodyFatPercentage()/100)*bodyDetails.getWeight());
//            macros.setBmrType(BMRType.KMA);
//
//        }
//        else{
//            macros.setBodyFatPercentage(calculatorUtil.calculateBFPWithBMI(bmi,age,bodyDetails.getGenderType()));
//            macros.setBfpType(BFPType.BMI);
//            macros.setBmr(calculatorUtil.calculateBMRWithMSJ(bodyDetails.getHeight(),bodyDetails.getWeight(),age,bodyDetails.getGenderType()));
//            macros.setBmrType(BMRType.MSJ);
//        }
//        macros.setLeanBodyMass(bodyDetails.getWeight()-macros.getBodyFatPercentage()/100*bodyDetails.getWeight());
//        macros.setBodyFatMass(bodyDetails.getWeight()*macros.getBodyFatPercentage()/100);
//        macros.setBodyFatMassClassificationType(BodyFatMassClassificationType.getType(macros.getBodyFatPercentage(),bodyDetails.getGenderType(),age));
//        return macros;
//    }
        return null;
    }


}

