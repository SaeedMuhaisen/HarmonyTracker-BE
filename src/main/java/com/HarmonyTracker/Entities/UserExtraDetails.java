package com.HarmonyTracker.Entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_extra_details")
public class UserExtraDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double waistNarrowest;
    private double waistNavel;
    private double hipWidest;
    private double thighWidest;
    private double neckNarrowest;
    private double bicepsWidest;
    private double forearmWidest;
    private double wristNarrowest;
}
