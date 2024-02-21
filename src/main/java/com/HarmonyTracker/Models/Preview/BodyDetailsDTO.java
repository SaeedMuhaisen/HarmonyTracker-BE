package com.HarmonyTracker.Models.Preview;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BodyDetailsDTO {
    public String gender;
    public long birthDate;
    public int weightI;
    public int weightF;
    public String weightUnit;
    public int height;
    public int heightI;
    public int heightF;
    public String heightUnit;
    public boolean extraData;
    public Triplets waistNarrowest;
    public Triplets waistNavel;
    public Triplets hipWidest;
    public Triplets thighWidest;
    public Triplets neckNarrowest;
    public Triplets bicepsWidest;
    public Triplets forearmWidest;
    public Triplets wristNarrowest;
}
