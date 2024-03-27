package com.HarmonyTracker.CSR.Controllers;

import com.HarmonyTracker.CSR.Services.PreviewServices;
import com.HarmonyTracker.Entities.DemoRequestForMultipleObjects;
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
    @PostMapping(value="/macros")
    public ResponseEntity<?> createMacroPlan(@RequestBody DemoRequestForMultipleObjects demoRequestForMultipleObjects ) {
        int i=0;
        return ResponseEntity.ok(null);
    }
}
