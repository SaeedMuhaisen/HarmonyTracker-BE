package com.HarmonyTracker.Models.Preview;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ExtraData {

    private double waistNarrowest;
    private double waistNavel;
    private double hipWidest;
    private double thighWidest;
    private double neckNarrowest;
    private double bicepsWidest;
    private double forearmWidest;
    private double wristNarrowest;
}
