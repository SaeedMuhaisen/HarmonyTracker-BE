package com.HarmonyTracker.CSR.Services;

import com.HarmonyTracker.Models.Preview.BodyDetails;
import com.HarmonyTracker.Models.Preview.BodyDetailsDTO;
import com.HarmonyTracker.Models.Preview.DemoMacro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PreviewServices {

    public DemoMacro createMacroPlan(BodyDetails bodyDetails) {
        var macros=DemoMacro.builder()
                .bmi( bodyDetails.getHeight() / (bodyDetails.getWeight()* bodyDetails.getWeight()))

                .build();

        return null;
    }
}
