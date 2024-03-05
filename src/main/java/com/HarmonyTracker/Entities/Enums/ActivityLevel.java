package com.HarmonyTracker.Entities.Enums;

import lombok.Getter;

@Getter
public enum ActivityLevel {
    CONSTRICTED_LIFESTYLE(1.1),
    WORKING_FROM_HOME(1.16),
    Sedentary(1.2),
    SLIGHTLY_ACTIVE(1.375),
    LIGHTLY_ACTIVE(1.425),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTREMELY_ACTIVE(1.9);

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