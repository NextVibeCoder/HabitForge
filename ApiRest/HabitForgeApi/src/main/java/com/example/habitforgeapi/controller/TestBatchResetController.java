package com.example.habitforgeapi.controller;

import com.example.habitforgeapi.service.CumplimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/test")
public class TestBatchResetController {

    private final CumplimientoService cumplimientoService;

    public TestBatchResetController(CumplimientoService cumplimientoService) {
        this.cumplimientoService = cumplimientoService;
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> triggerBatchReset() {
        cumplimientoService.procesarCierreDeDia();
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "Proceso de cierre de día ejecutado manualmente"
        ));
    }
}
