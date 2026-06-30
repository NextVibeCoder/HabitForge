package com.example.habitforgeapi.service;

import com.example.habitforgeapi.config.TimeProvider;
import com.example.habitforgeapi.dto.HabitoParticipanteResponseDTO;
import com.example.habitforgeapi.dto.HabitoRequestDTO;
import com.example.habitforgeapi.dto.HabitoResponseDTO;
import com.example.habitforgeapi.exception.BadRequestException;
import com.example.habitforgeapi.exception.InactiveHabitException;
import com.example.habitforgeapi.exception.ResourceNotFoundException;
import com.example.habitforgeapi.exception.UnauthorizedHabitAccessException;
import com.example.habitforgeapi.model.*;
import com.example.habitforgeapi.repository.*;
import com.example.habitforgeapi.strategy.FrecuenciaStrategy;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class HabitoService {

    private final HabitoRepository habitoRepository;
    private final HabitoDiaSemanaRepository habitoDiaSemanaRepository;
    private final HabitoParticipanteRepository habitoParticipanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final TimeProvider timeProvider;
    private final Map<String, FrecuenciaStrategy> strategyMap;
    private final RegistroCumplimientoRepository registroCumplimientoRepository;

    public HabitoService(HabitoRepository habitoRepository,
                         HabitoDiaSemanaRepository habitoDiaSemanaRepository,
                         HabitoParticipanteRepository habitoParticipanteRepository,
                         UsuarioRepository usuarioRepository,
                         TimeProvider timeProvider,
                         Map<String, FrecuenciaStrategy> strategyMap,
                         RegistroCumplimientoRepository registroCumplimientoRepository) {
        this.habitoRepository = habitoRepository;
        this.habitoDiaSemanaRepository = habitoDiaSemanaRepository;
        this.habitoParticipanteRepository = habitoParticipanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.timeProvider = timeProvider;
        this.strategyMap = strategyMap;
        this.registroCumplimientoRepository = registroCumplimientoRepository;
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
                throw new BadRequestException("Debe seleccionar al menos un día de la semana para la frecuencia semanal");
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
                dto.isEsCompartido(),
                timeProvider.getCurrentDateTime()
        );
        habito = habitoRepository.save(habito);

        saveDiasSemana(habito.getId(), dto.getFrecuencia(), dto.getDiasSemana());

        HabitoParticipante creatorPart = new HabitoParticipante(
                creator.getId(),
                habito.getId(),
                EstadoInvitacion.ACEPTADA,
                timeProvider.getCurrentDateTime()
        );
        habitoParticipanteRepository.save(creatorPart);

        if (dto.isEsCompartido() && dto.getAmigosInvitados() != null) {
            for (String email : dto.getAmigosInvitados()) {
                if (email.equalsIgnoreCase(creator.getEmail())) {
                    throw new BadRequestException("No puedes invitarte a ti mismo como amigo participante");
                }
                Usuario amigo = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario invitado no encontrado con email: " + email));
                HabitoParticipante amigoPart = new HabitoParticipante(
                        amigo.getId(),
                        habito.getId(),
                        EstadoInvitacion.PENDIENTE,
                        null
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
    public HabitoResponseDTO getHabitoById(Long id) {
        Usuario user = getAuthenticatedUser();
        Habito habito = habitoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + id));

        boolean esCreador = habito.getCreadorId().equals(user.getId());
        boolean esParticipanteAceptado = habitoParticipanteRepository.findByHabitoIdAndUsuarioId(id, user.getId())
                .map(hp -> hp.getEstadoInvitacion() == EstadoInvitacion.ACEPTADA)
                .orElse(false);

        if (!esCreador && !esParticipanteAceptado) {
            throw new UnauthorizedHabitAccessException("No tienes permisos para visualizar este hábito");
        }

        return mapToResponseDTO(habito);
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
        hp.setFechaUnion(timeProvider.getCurrentDateTime());
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

    public HabitoResponseDTO invitarAmigos(Long id, List<String> emails) {
        Usuario creator = getAuthenticatedUser();
        Habito habito = habitoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito no encontrado con ID: " + id));

        if (!habito.getCreadorId().equals(creator.getId())) {
            throw new UnauthorizedHabitAccessException("No tienes permisos para modificar este hábito");
        }

        if (!habito.isEsCompartido()) {
            throw new BadRequestException("No se pueden invitar participantes a un hábito individual");
        }

        if (emails == null || emails.isEmpty()) {
            throw new BadRequestException("La lista de correos no puede estar vacía");
        }

        for (String email : emails) {
            if (email.equalsIgnoreCase(creator.getEmail())) {
                throw new BadRequestException("No puedes invitarte a ti mismo");
            }

            Usuario amigo = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario invitado no encontrado con email: " + email));

            if (habitoParticipanteRepository.findByHabitoIdAndUsuarioId(id, amigo.getId()).isPresent()) {
                throw new BadRequestException("El usuario con email " + email + " ya está invitado o participa en este hábito");
            }

            HabitoParticipante amigoPart = new HabitoParticipante(
                    amigo.getId(),
                    id,
                    EstadoInvitacion.PENDIENTE,
                    null
            );
            habitoParticipanteRepository.save(amigoPart);
        }

        return mapToResponseDTO(habito);
    }

    @Transactional(readOnly = true)
    public List<HabitoResponseDTO> getInvitacionesPendientes() {
        Usuario user = getAuthenticatedUser();
        List<HabitoParticipante> participaciones = habitoParticipanteRepository.findSharedHabitsForUser(
                user.getId(),
                EstadoInvitacion.PENDIENTE
        );
        return participaciones.stream()
                .map(hp -> habitoRepository.findByIdAndActivoTrue(hp.getHabitoId())
                        .map(this::mapToResponseDTO)
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    private HabitoResponseDTO mapToResponseDTO(Habito habito) {
        LocalDate today = timeProvider.getCurrentDate();
        FrecuenciaStrategy strategy = strategyMap.get(habito.getFrecuencia().name());
        boolean esDiaObligatorio = strategy != null && strategy.isMandatoryDay(habito, today);

        List<DiaSemana> dias = habitoDiaSemanaRepository.findByHabitoId(habito.getId()).stream()
                .map(HabitoDiaSemana::getDiaSemana)
                .toList();

        List<HabitoParticipanteResponseDTO> parts = habitoParticipanteRepository.findByHabitoId(habito.getId()).stream()
                .map(hp -> {
                    String username = usuarioRepository.findById(hp.getUsuarioId())
                            .map(Usuario::getUsername)
                            .orElse("Usuario Desconocido");
                    boolean completadoHoy = registroCumplimientoRepository
                            .existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(hp.getId(), today);
                    return new HabitoParticipanteResponseDTO(
                            hp.getId(),
                            hp.getFechaUnion(),
                            hp.getEstadoInvitacion(),
                            hp.getRachaMasLarga(),
                            hp.getRachaActual(),
                            hp.getUsuarioId(),
                            username,
                            completadoHoy
                    );
                })
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
                habito.getRachaGrupalActual(),
                habito.getRachaGrupalMasLarga(),
                dias,
                parts,
                esDiaObligatorio
        );
    }
}
