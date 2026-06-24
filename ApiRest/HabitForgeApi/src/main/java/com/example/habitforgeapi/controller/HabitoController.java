package com.example.habitforgeapi.controller;

import com.example.habitforgeapi.dto.HabitoRequestDTO;
import com.example.habitforgeapi.dto.HabitoResponseDTO;
import com.example.habitforgeapi.service.HabitoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitos")
public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @PostMapping
    public ResponseEntity<HabitoResponseDTO> createHabito(@RequestBody @Valid HabitoRequestDTO dto) {
        HabitoResponseDTO response = habitoService.createHabito(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoResponseDTO> updateHabito(@PathVariable Long id, @RequestBody @Valid HabitoRequestDTO dto) {
        HabitoResponseDTO response = habitoService.updateHabito(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabito(@PathVariable Long id) {
        habitoService.deleteHabito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/individuales")
    public ResponseEntity<List<HabitoResponseDTO>> getHabitosIndividuales() {
        List<HabitoResponseDTO> response = habitoService.getHabitosIndividuales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/compartidos")
    public ResponseEntity<List<HabitoResponseDTO>> getHabitosCompartidos() {
        List<HabitoResponseDTO> response = habitoService.getHabitosCompartidos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invitaciones")
    public ResponseEntity<List<HabitoResponseDTO>> getInvitacionesPendientes() {
        List<HabitoResponseDTO> response = habitoService.getInvitacionesPendientes();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<Void> aceptarInvitacion(@PathVariable Long id) {
        habitoService.aceptarInvitacion(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazarInvitacion(@PathVariable Long id) {
        habitoService.rechazarInvitacion(id);
        return ResponseEntity.ok().build();
    }
}
