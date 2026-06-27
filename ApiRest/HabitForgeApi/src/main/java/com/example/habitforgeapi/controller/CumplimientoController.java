package com.example.habitforgeapi.controller;

import com.example.habitforgeapi.dto.CumplimientoRequestDTO;
import com.example.habitforgeapi.dto.CumplimientoResponseDTO;
import com.example.habitforgeapi.service.CumplimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cumplimientos")
public class CumplimientoController {

    private final CumplimientoService cumplimientoService;

    public CumplimientoController(CumplimientoService cumplimientoService) {
        this.cumplimientoService = cumplimientoService;
    }

    @PostMapping
    public ResponseEntity<CumplimientoResponseDTO> registrarCumplimiento(
            @RequestBody @Valid CumplimientoRequestDTO dto) {
        CumplimientoResponseDTO response = cumplimientoService.registrarCumplimiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
