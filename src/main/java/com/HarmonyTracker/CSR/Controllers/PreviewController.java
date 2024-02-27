package com.HarmonyTracker.CSR.Controllers;

import com.HarmonyTracker.CSR.Services.PreviewServices;
import com.HarmonyTracker.Models.Preview.BodyDetails;
import com.HarmonyTracker.Models.Preview.BodyDetailsDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/preview")
public class PreviewController {
    private final PreviewServices previewServices;
    @PostMapping("/macros")
    public ResponseEntity<?> createMacroPlan(@RequestBody BodyDetailsDTO bodyDetailsDTO){
        var body=new BodyDetails(bodyDetailsDTO);
        return ResponseEntity.ok(previewServices.createMacroPlan(body));
    }
}
