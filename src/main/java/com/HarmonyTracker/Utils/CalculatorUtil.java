package com.HarmonyTracker.Utils;

import com.HarmonyTracker.Entities.Enums.GenderType;

public class CalculatorUtil {

    public double MaleBodyFatPercentage(double waist, double height, double neck) {
        return (495 / (1.0324 - 0.19077 * Math.log10(waist - neck) + 0.15456 * Math.log10(height))) - 450;
    }
    public double FemaleBodyFatPercentage(double waist, double height, double neck,double hip) {
        return (495/ (1.29579 - 0.35004*Math.log10(waist+hip-neck)+0.22100*Math.log10(height)))-450;
    }


}
