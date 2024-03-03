package com.HarmonyTracker.Utils;

import com.HarmonyTracker.Entities.Enums.GenderType;

public class CalculatorUtil {

    public double calculateBMI(double weight,double height){
        return weight / Math.pow(height * 0.01, 2);
    }

    public double calculateBFPWithBMI(double bmi, double age,GenderType genderType) {
        if (genderType.equals(GenderType.male)) {
            return (1.20 * bmi) + (0.23 * age) - 16.2;
        } else {
            return (1.20 * bmi) + (0.23 * age) - 5.4;
        }
    }

    public double calculateBMRWithMSJ(double height,double weight,int age,GenderType genderType){
        if(genderType.equals(GenderType.male)){
            return 10 * weight + 6.25 * height - 5 * age + 5;
        }
        else{
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
    }

    public double calculateBFPWithUnits(double waist, double height, double neck,double hip,GenderType genderType) {
        if (genderType.equals(GenderType.male)) {
            return (495 / (1.0324 - 0.19077 * Math.log10(waist - neck) + 0.15456 * Math.log10(height))) - 450;
        } else {
            return (495 / (1.29579 - 0.35004 * Math.log10(waist + hip - neck) + 0.22100 * Math.log10(height))) - 450;
        }
    }

}
