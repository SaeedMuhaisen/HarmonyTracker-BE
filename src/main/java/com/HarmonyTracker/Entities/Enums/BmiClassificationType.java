package com.HarmonyTracker.Entities.Enums;

public enum BmiClassificationType {
    SEVERE_THINNESS(0,16),
    MODERATE_THINNESS(16,17),
    MILD_THINNESS(17,18.5),
    NORMAL(18.5,25),
    OVERWEIGHT(25,30),
    OBESE_CLASS_I(30,35),
    OBESE_CLASS_II(35,40),
    OBESE_CLASS_III(40,10000);

    private final double lowerBound;
    private final double upperBound;

    BmiClassificationType(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static BmiClassificationType getType(double index) {
        for (BmiClassificationType type : BmiClassificationType.values()) {
            if (index >= type.lowerBound && index < type.upperBound) {
                return type;
            }
        }
        return null;
    }
}

