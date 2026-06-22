package com.example.habitforgeapi.controller;

import com.example.habitforgeapi.dto.LoginDTO;
import com.example.habitforgeapi.dto.LoginResponseDTO;
import com.example.habitforgeapi.dto.RegistroDTO;
import com.example.habitforgeapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody @Valid RegistroDTO dto) {
        String mensaje = service.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}
