package com.HarmonyTracker.Entities;

import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.GenderType;
import com.HarmonyTracker.Entities.Enums.UnitType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="body_details")
public class BodyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private GenderType genderType;
    private LocalDate birthDate;

    private UnitType preferredUnit;
    private double height;
    private UnitType preferredWeightUnit;
    private double weight;
    private boolean extraData;
    private double neckNarrowest;
    private double waistNavel;
    private double hipWidest;
    private ActivityLevel activityLevel;
    private int goal;

//    public BodyDetails(BodyDetailsDTO bodyDetailsDTO) {
//        this.genderType = bodyDetailsDTO.gender.equals("male") ? GenderType.male : GenderType.female;
//        this.birthDate = Instant.ofEpochMilli(bodyDetailsDTO.birthDate).atZone(ZoneId.systemDefault()).toLocalDate();
//
//        this.weight = bodyDetailsDTO.weight;
//        this.height = bodyDetailsDTO.height;
//
//        if(bodyDetailsDTO.extraData){
//            this.extraData=ExtraData.builder()
//                    .waistNarrowest(bodyDetailsDTO.waistNarrowest)
//                    .waistNavel(bodyDetailsDTO.waistNavel)
//                    .hipWidest(bodyDetailsDTO.hipWidest)
//                    .thighWidest(bodyDetailsDTO.thighWidest)
//                    .neckNarrowest(bodyDetailsDTO.neckNarrowest)
//                    .bicepsWidest(bodyDetailsDTO.bicepsWidest)
//                    .forearmWidest(bodyDetailsDTO.forearmWidest)
//                    .wristNarrowest(bodyDetailsDTO.wristNarrowest)
//                    .build();
//        }
//    }
}
