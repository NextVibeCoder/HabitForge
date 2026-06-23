package com.example.habitforgeapi.service;

import com.example.habitforgeapi.dto.HabitoParticipanteResponseDTO;
import com.example.habitforgeapi.dto.HabitoRequestDTO;
import com.example.habitforgeapi.dto.HabitoResponseDTO;
import com.example.habitforgeapi.exception.InactiveHabitException;
import com.example.habitforgeapi.exception.ResourceNotFoundException;
import com.example.habitforgeapi.exception.UnauthorizedHabitAccessException;
import com.example.habitforgeapi.model.*;
import com.example.habitforgeapi.repository.HabitoDiaSemanaRepository;
import com.example.habitforgeapi.repository.HabitoParticipanteRepository;
import com.example.habitforgeapi.repository.HabitoRepository;
import com.example.habitforgeapi.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class HabitoService {

    private final HabitoRepository habitoRepository;
    private final HabitoDiaSemanaRepository habitoDiaSemanaRepository;
    private final HabitoParticipanteRepository habitoParticipanteRepository;
    private final UsuarioRepository usuarioRepository;

    public HabitoService(HabitoRepository habitoRepository,
                         HabitoDiaSemanaRepository habitoDiaSemanaRepository,
                         HabitoParticipanteRepository habitoParticipanteRepository,
                         UsuarioRepository usuarioRepository) {
        this.habitoRepository = habitoRepository;
        this.habitoDiaSemanaRepository = habitoDiaSemanaRepository;
        this.habitoParticipanteRepository = habitoParticipanteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    private void saveDiasSemana(Long habitoId, Frecuencia frecuencia, List<DiaSemana> diasSemanaDto) {
        if (frecuencia == Frecuencia.DIARIA) {
            for (DiaSemana dia : DiaSemana.values()) {
                HabitoDiaSemana hds = new HabitoDiaSemana(habitoId, dia);
                habitoDiaSemanaRepository.save(hds);
            }
        } else if (frecuencia == Frecuencia.SEMANAL) {
            if (diasSemanaDto == null || diasSemanaDto.isEmpty()) {
                throw new com.example.habitforgeapi.exception.BadRequestException("Debe seleccionar al menos un día de la semana para la frecuencia semanal");
            }
            for (DiaSemana dia : diasSemanaDto) {
                HabitoDiaSemana hds = new HabitoDiaSemana(habitoId, dia);
                habitoDiaSemanaRepository.save(hds);
            }
        }
    }

    public HabitoResponseDTO createHabito(HabitoRequestDTO dto) {
        Usuario creator = getAuthenticatedUser();

        Habito habito = new Habito(
                creator.getId(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFrecuencia(),
                dto.getIcon(),
                dto.isEsCompartido()
        );
        habito = habitoRepository.save(habito);

        saveDiasSemana(habito.getId(), dto.getFrecuencia(), dto.getDiasSemana());

        HabitoParticipante creatorPart = new HabitoParticipante(
                creator.getId(),
                habito.getId(),
                EstadoInvitacion.ACEPTADA
        );
        habitoParticipanteRepository.save(creatorPart);

        if (dto.isEsCompartido() && dto.getAmigosInvitados() != null) {
            for (String email : dto.getAmigosInvitados()) {
                Usuario amigo = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario invitado no encontrado con email: " + email));
                HabitoParticipante amigoPart = new HabitoParticipante(
                        amigo.getId(),
                        habito.getId(),
                        EstadoInvitacion.PENDIENTE
                );
                habitoParticipanteRepository.save(amigoPart);
            }
        }

        return mapToResponseDTO(habito);
    }

    public HabitoResponseDTO updateHabito(Long id, HabitoRequestDTO dto) {
        Usuario user = getAuthenticatedUser();
        Habito habito = habitoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + id));

        if (!habito.isActivo()) {
            throw new InactiveHabitException("El hábito está inactivo y no se puede modificar");
        }

        if (!habito.getCreadorId().equals(user.getId())) {
            throw new UnauthorizedHabitAccessException("No tienes permisos para modificar este hábito");
        }

        habito.setNombre(dto.getNombre());
        habito.setDescripcion(dto.getDescripcion());
        habito.setFrecuencia(dto.getFrecuencia());
        habito.setIcon(dto.getIcon());
        habito.setEsCompartido(dto.isEsCompartido());
        habito = habitoRepository.save(habito);

        habitoDiaSemanaRepository.deleteByHabitoId(id);
        habitoDiaSemanaRepository.flush();
        saveDiasSemana(id, dto.getFrecuencia(), dto.getDiasSemana());

        return mapToResponseDTO(habito);
    }

    public void deleteHabito(Long id) {
        Usuario user = getAuthenticatedUser();
        Habito habito = habitoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + id));

        if (!habito.isActivo()) {
            throw new InactiveHabitException("El hábito ya está inactivo");
        }

        if (!habito.getCreadorId().equals(user.getId())) {
            throw new UnauthorizedHabitAccessException("No tienes permisos para eliminar este hábito");
        }

        habito.setActivo(false);
        habitoRepository.save(habito);
    }

    @Transactional(readOnly = true)
    public List<HabitoResponseDTO> getHabitosIndividuales() {
        Usuario user = getAuthenticatedUser();
        List<Habito> habitos = habitoRepository.findByCreadorIdAndEsCompartidoFalseAndActivoTrue(user.getId());
        return habitos.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HabitoResponseDTO> getHabitosCompartidos() {
        Usuario user = getAuthenticatedUser();
        List<HabitoParticipante> participaciones = habitoParticipanteRepository.findSharedHabitsForUser(
                user.getId(),
                EstadoInvitacion.ACEPTADA
        );
        return participaciones.stream()
                .map(hp -> habitoRepository.findByIdAndActivoTrue(hp.getHabitoId())
                        .map(this::mapToResponseDTO)
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public void aceptarInvitacion(Long habitoId) {
        Usuario user = getAuthenticatedUser();
        HabitoParticipante hp = habitoParticipanteRepository.findByHabitoIdAndUsuarioId(habitoId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No tienes una invitación para el hábito con ID: " + habitoId));

        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + habitoId));

        if (!habito.isActivo()) {
            throw new InactiveHabitException("El hábito asociado está inactivo");
        }

        hp.setEstadoInvitacion(EstadoInvitacion.ACEPTADA);
        hp.setFechaUnion(LocalDateTime.now());
        habitoParticipanteRepository.save(hp);
    }

    public void rechazarInvitacion(Long habitoId) {
        Usuario user = getAuthenticatedUser();
        HabitoParticipante hp = habitoParticipanteRepository.findByHabitoIdAndUsuarioId(habitoId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No tienes una invitación para el hábito con ID: " + habitoId));

        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + habitoId));

        if (!habito.isActivo()) {
            throw new InactiveHabitException("El hábito asociado está inactivo");
        }

        hp.setEstadoInvitacion(EstadoInvitacion.RECHAZADA);
        hp.setFechaUnion(null);
        habitoParticipanteRepository.save(hp);
    }

    private HabitoResponseDTO mapToResponseDTO(Habito habito) {
        List<DiaSemana> dias = habitoDiaSemanaRepository.findByHabitoId(habito.getId()).stream()
                .map(HabitoDiaSemana::getDiaSemana)
                .toList();

        List<HabitoParticipanteResponseDTO> parts = habitoParticipanteRepository.findByHabitoId(habito.getId()).stream()
                .map(hp -> new HabitoParticipanteResponseDTO(
                        hp.getId(),
                        hp.getUsuarioId(),
                        hp.getRachaActual(),
                        hp.getRachaMasLarga(),
                        hp.getEstadoInvitacion(),
                        hp.getFechaUnion()
                ))
                .toList();

        return new HabitoResponseDTO(
                habito.getId(),
                habito.getCreadorId(),
                habito.getNombre(),
                habito.getDescripcion(),
                habito.getFrecuencia(),
                habito.getIcon(),
                habito.isEsCompartido(),
                habito.isActivo(),
                habito.getFechaCreacion(),
                dias,
                parts
        );
    }
}
