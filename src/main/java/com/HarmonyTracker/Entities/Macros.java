package com.HarmonyTracker.Entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_macros")
public class Macros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double fat;
    private double carbs;
    private double protein;
    private double calories;
}
