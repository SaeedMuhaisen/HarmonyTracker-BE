package DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class BodyDetailsDTO {
    public String gender;
    public long birthDate;
    public String preferredUnit;
    public String preferredWeightUnit;

    public double height;
    public double weight;

    public boolean extraData;
    public double neckNarrowest;
    public double waistNavel;
    public double hipWidest;
    public double activityLevel;
    private int goal;
}
