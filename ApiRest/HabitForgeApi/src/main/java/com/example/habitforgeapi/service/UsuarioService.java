package com.example.habitforgeapi.service;


import com.example.habitforgeapi.dto.LoginDTO;
import com.example.habitforgeapi.dto.LoginResponseDTO;
import com.example.habitforgeapi.dto.RegistroDTO;
import com.example.habitforgeapi.exception.BadRequestException;
import com.example.habitforgeapi.model.Usuario;
import com.example.habitforgeapi.repository.UsuarioRepository;
import com.example.habitforgeapi.security.CustomerDetailsService;
import com.example.habitforgeapi.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository repo,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          CustomerDetailsService customerDetailsService,
                          JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public String signUp(RegistroDTO dto) {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email ya usado.");
        }

        String passwordEncriptado = passwordEncoder.encode(dto.getPassword());
        Usuario usuario = new Usuario(dto.getUsername(), dto.getEmail(), passwordEncriptado);

        repo.save(usuario);
        return "Usuario creado con éxito";
    }

    public LoginResponseDTO login(LoginDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail());
        return new LoginResponseDTO(token);
    }
}
