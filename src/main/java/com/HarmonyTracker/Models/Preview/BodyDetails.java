package com.HarmonyTracker.Models.Preview;

import com.HarmonyTracker.Entities.Enums.GenderType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
//all numbers will be calculated with metric system
public class BodyDetails {

    public BodyDetails(BodyDetailsDTO bodyDetailsDTO) {
        this.genderType = bodyDetailsDTO.gender.equals("Male") ? GenderType.male : GenderType.female;
        this.birthDate = new Date(bodyDetailsDTO.birthDate);

        this.weight = bodyDetailsDTO.weightUnit.equals("kg") ?
                (bodyDetailsDTO.weightI+ (double) bodyDetailsDTO.weightF/10):
                (bodyDetailsDTO.weightI+ (double) bodyDetailsDTO.weightF/10)/2.20462;


        this.height = bodyDetailsDTO.heightUnit.equals("cm") ? bodyDetailsDTO.height : converter(bodyDetailsDTO.heightI, bodyDetailsDTO.heightF);
        if(bodyDetailsDTO.extraData){
            this.extraData=ExtraData.builder()
                    .waistNarrowest(extraDataExtractor(bodyDetailsDTO.waistNarrowest))
                    .waistNavel(extraDataExtractor(bodyDetailsDTO.waistNavel))
                    .hipWidest(extraDataExtractor(bodyDetailsDTO.hipWidest))
                    .thighWidest(extraDataExtractor(bodyDetailsDTO.thighWidest))
                    .neckNarrowest(extraDataExtractor(bodyDetailsDTO.neckNarrowest))
                    .bicepsWidest(extraDataExtractor(bodyDetailsDTO.bicepsWidest))
                    .forearmWidest(extraDataExtractor(bodyDetailsDTO.forearmWidest))
                    .wristNarrowest(extraDataExtractor(bodyDetailsDTO.wristNarrowest))
                    .build();
        }

    }
    private double extraDataExtractor(Triplets t){
        if(t.getUnit().equals("cm")){
            return combine(t.getFirst(),t.getLast());
        }
        else{
            return converter(t.getFirst(),t.getLast());
        }
    }
    private double converter(double x, double y){
        double feets= x+ y/12;
        return Math.round(feets*(30.48));
    }
    private double combine(double x,double y){
        return x+(y/10);
    }

    private GenderType genderType;
    private Date birthDate;
    private double weight;
    private double height;
    private ExtraData extraData;

}
