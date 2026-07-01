package com.example.habitforgeapi.service;


import com.example.habitforgeapi.dto.HistorialCumplimientoDTO;
import com.example.habitforgeapi.dto.LoginDTO;
import com.example.habitforgeapi.dto.LoginResponseDTO;
import com.example.habitforgeapi.dto.RegistroDTO;
import com.example.habitforgeapi.dto.UserProfileResponseDTO;
import com.example.habitforgeapi.exception.BadRequestException;
import com.example.habitforgeapi.exception.ResourceNotFoundException;
import com.example.habitforgeapi.model.Usuario;
import com.example.habitforgeapi.repository.HabitoParticipanteRepository;
import com.example.habitforgeapi.repository.RegistroCumplimientoRepository;
import com.example.habitforgeapi.repository.UsuarioRepository;
import com.example.habitforgeapi.security.CustomerDetailsService;
import com.example.habitforgeapi.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;
    private final HabitoParticipanteRepository habitoParticipanteRepository;
    private final RegistroCumplimientoRepository registroCumplimientoRepository;

    public UsuarioService(UsuarioRepository repo,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          CustomerDetailsService customerDetailsService,
                          JwtUtil jwtUtil,
                          HabitoParticipanteRepository habitoParticipanteRepository,
                          RegistroCumplimientoRepository registroCumplimientoRepository) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
        this.habitoParticipanteRepository = habitoParticipanteRepository;
        this.registroCumplimientoRepository = registroCumplimientoRepository;
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

    @Transactional(readOnly = true)
    public UserProfileResponseDTO getPerfil() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        long cantidadHabitosActivos = habitoParticipanteRepository.countActiveHabitosByUsuarioId(usuario.getId());
        int rachaMasLarga = habitoParticipanteRepository.findMaxRachaMasLargaByUsuarioId(usuario.getId());
        java.util.List<HistorialCumplimientoDTO> historial = registroCumplimientoRepository.findHistorialByUsuarioId(usuario.getId());

        return new UserProfileResponseDTO(
                usuario.getUsername(),
                usuario.getEmail(),
                rachaMasLarga,
                (int) cantidadHabitosActivos,
                usuario.getFechaRegistro(),
                historial
        );
    }
}
