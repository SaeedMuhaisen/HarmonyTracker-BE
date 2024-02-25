package com.HarmonyTracker.Models.Preview;

import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.GenderType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


@Data
@Getter
@Setter

public class BodyDetails {
    public BodyDetails(BodyDetailsDTO bodyDetailsDTO) {
        this.genderType = bodyDetailsDTO.gender.equals("male") ? GenderType.male : GenderType.female;
        this.birthDate = Instant.ofEpochSecond(bodyDetailsDTO.birthDate).atZone(ZoneId.systemDefault()).toLocalDate();

        this.weight = bodyDetailsDTO.weight;
        this.height = bodyDetailsDTO.height;
        this.activityLevel=ActivityLevel.getType(bodyDetailsDTO.activityLevel);

        if(bodyDetailsDTO.extraData){
            this.extraData=ExtraData.builder()
                    .waistNarrowest(bodyDetailsDTO.waistNarrowest)
                    .waistNavel(bodyDetailsDTO.waistNavel)
                    .hipWidest(bodyDetailsDTO.hipWidest)
                    .thighWidest(bodyDetailsDTO.thighWidest)
                    .neckNarrowest(bodyDetailsDTO.neckNarrowest)
                    .bicepsWidest(bodyDetailsDTO.bicepsWidest)
                    .forearmWidest(bodyDetailsDTO.forearmWidest)
                    .wristNarrowest(bodyDetailsDTO.wristNarrowest)
                    .build();
        }
    }
    private GenderType genderType;
    private LocalDate birthDate;
    private double weight;
    private double height;
    private ExtraData extraData;
    private ActivityLevel activityLevel;

}
