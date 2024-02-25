package com.HarmonyTracker.Entities.Enums;

public enum ActivityLevel {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    SUPER_ACTIVE(1.9);

    private final double value;

    ActivityLevel(double value) {
        this.value = value;
    }

    public static ActivityLevel getType(double activityLevel) {
        for (ActivityLevel type : ActivityLevel.values()) {
            if (activityLevel == type.value) {
                return type;
            }
        }
        return null;
    }
}