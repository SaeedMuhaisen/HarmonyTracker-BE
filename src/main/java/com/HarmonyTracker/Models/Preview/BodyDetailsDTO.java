package com.HarmonyTracker.Models.Preview;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BodyDetailsDTO {
    public String gender;
    public double height;
    public String preferredUnit;
    public String preferredWeightUnit;
    public long birthDate;
    public double weight;
    public boolean extraData;
    public double waistNarrowest;
    public double waistNavel;
    public double hipWidest;
    public double thighWidest;
    public double neckNarrowest;
    public double bicepsWidest;
    public double forearmWidest;
    public double wristNarrowest;
    public double activityLevel;
}
