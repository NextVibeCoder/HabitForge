package com.example.habitforgeapi.service;

import com.example.habitforgeapi.config.TimeProvider;
import com.example.habitforgeapi.dto.CumplimientoRequestDTO;
import com.example.habitforgeapi.dto.CumplimientoResponseDTO;
import com.example.habitforgeapi.exception.*;
import com.example.habitforgeapi.model.*;
import com.example.habitforgeapi.repository.*;
import com.example.habitforgeapi.strategy.FrecuenciaStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CumplimientoService {

    private static final Logger log = LoggerFactory.getLogger(CumplimientoService.class);

    private final RegistroCumplimientoRepository registroCumplimientoRepository;
    private final CumplimientoDiarioHabitoRepository cumplimientoDiarioHabitoRepository;
    private final HabitoParticipanteRepository habitoParticipanteRepository;
    private final HabitoRepository habitoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Map<String, FrecuenciaStrategy> strategyMap;
    private final TimeProvider timeProvider;

    public CumplimientoService(RegistroCumplimientoRepository registroCumplimientoRepository,
                               CumplimientoDiarioHabitoRepository cumplimientoDiarioHabitoRepository,
                               HabitoParticipanteRepository habitoParticipanteRepository,
                               HabitoRepository habitoRepository,
                               UsuarioRepository usuarioRepository,
                               Map<String, FrecuenciaStrategy> strategyMap,
                               TimeProvider timeProvider) {
        this.registroCumplimientoRepository = registroCumplimientoRepository;
        this.cumplimientoDiarioHabitoRepository = cumplimientoDiarioHabitoRepository;
        this.habitoParticipanteRepository = habitoParticipanteRepository;
        this.habitoRepository = habitoRepository;
        this.usuarioRepository = usuarioRepository;
        this.strategyMap = strategyMap;
        this.timeProvider = timeProvider;
    }

    private FrecuenciaStrategy resolveStrategy(Frecuencia frecuencia) {
        return strategyMap.get(frecuencia.name());
    }

    private Usuario getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Transactional
    public CumplimientoResponseDTO registrarCumplimiento(CumplimientoRequestDTO dto) {
        Usuario user = getAuthenticatedUser();
        LocalDate today = timeProvider.getCurrentDate();

        Habito habito = habitoRepository.findByIdAndActivoTrue(dto.getHabitoId())
                .orElseThrow(() -> new InactiveHabitException("El hábito está inactivo o no existe"));

        HabitoParticipante hp = habitoParticipanteRepository
                .findByHabitoIdAndUsuarioId(habito.getId(), user.getId())
                .orElseThrow(() -> new UnauthorizedHabitAccessException("No tienes permisos para completar este hábito"));

        if (hp.getEstadoInvitacion() != EstadoInvitacion.ACEPTADA) {
            throw new BadRequestException("No puedes completar un hábito sin haber aceptado la invitación");
        }

        FrecuenciaStrategy strategy = resolveStrategy(habito.getFrecuencia());
        if (!strategy.isMandatoryDay(habito, today)) {
            throw new BadRequestException("El hábito no es obligatorio para la fecha de hoy");
        }

        if (registroCumplimientoRepository.existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(
                hp.getId(), today)) {
            throw new DuplicateCompletionException(
                    "Ya registraste el cumplimiento de este hábito para hoy");
        }

        RegistroCumplimiento registro = new RegistroCumplimiento(
                hp.getId(), today, true, timeProvider.getCurrentDateTime());
        registro = registroCumplimientoRepository.save(registro);

        updateIndividualStreak(hp, habito, today);

        evaluateGroupCompletionOnWrite(habito, today);

        return new CumplimientoResponseDTO(
                registro.getId(),
                hp.getId(),
                registro.getFecha(),
                registro.isCompletado(),
                registro.getFechaRegistro(),
                hp.getRachaActual(),
                hp.getRachaMasLarga()
        );
    }

    private void updateIndividualStreak(HabitoParticipante hp, Habito habito, LocalDate today) {
        FrecuenciaStrategy strategy = resolveStrategy(habito.getFrecuencia());

        LocalDate previousDay = today.minusDays(1);
        boolean foundPrevious = false;

        for (int i = 0; i < 7; i++) {
            if (strategy.isMandatoryDay(habito, previousDay)) {
                foundPrevious = true;
                break;
            }
            previousDay = previousDay.minusDays(1);
        }

        if (foundPrevious && registroCumplimientoRepository
                .existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(hp.getId(), previousDay)) {
            hp.setRachaActual(hp.getRachaActual() + 1);
        } else {
            hp.setRachaActual(1);
        }

        if (hp.getRachaActual() > hp.getRachaMasLarga()) {
            hp.setRachaMasLarga(hp.getRachaActual());
        }

        habitoParticipanteRepository.save(hp);
    }

    private void evaluateGroupCompletionOnWrite(Habito habito, LocalDate today) {
        List<HabitoParticipante> activeParticipants = habitoParticipanteRepository
                .findByHabitoIdAndEstadoInvitacion(habito.getId(), EstadoInvitacion.ACEPTADA);

        long totalActive = activeParticipants.size();

        long totalCompleted = activeParticipants.stream()
                .filter(p -> registroCumplimientoRepository
                        .existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(p.getId(), today))
                .count();

        if (totalCompleted == totalActive) {
            CumplimientoDiarioHabito cdh = cumplimientoDiarioHabitoRepository
                    .findByHabitoIdAndFecha(habito.getId(), today)
                    .orElse(new CumplimientoDiarioHabito(habito.getId(), today, false));

            cdh.setCompletaron(true);
            cumplimientoDiarioHabitoRepository.save(cdh);

            updateGroupStreak(habito, today);
        }
    }

    private void updateGroupStreak(Habito habito, LocalDate today) {
        FrecuenciaStrategy strategy = resolveStrategy(habito.getFrecuencia());

        LocalDate previousDay = today.minusDays(1);
        boolean foundPrevious = false;

        for (int i = 0; i < 7; i++) {
            if (strategy.isMandatoryDay(habito, previousDay)) {
                foundPrevious = true;
                break;
            }
            previousDay = previousDay.minusDays(1);
        }

        if (foundPrevious && cumplimientoDiarioHabitoRepository
                .existsByHabitoIdAndFechaAndCompletaronTrue(habito.getId(), previousDay)) {
            habito.setRachaGrupalActual(habito.getRachaGrupalActual() + 1);
        } else {
            habito.setRachaGrupalActual(1);
        }

        if (habito.getRachaGrupalActual() > habito.getRachaGrupalMasLarga()) {
            habito.setRachaGrupalMasLarga(habito.getRachaGrupalActual());
        }

        habitoRepository.save(habito);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void procesarCierreDeDiaScheduled() {
        log.info("=== Inicio del proceso de cierre de día programado ===");
        procesarCierreDeDia();
        log.info("=== Fin del proceso de cierre de día programado ===");
    }

    @Transactional
    public void procesarCierreDeDia() {
        LocalDate yesterday = timeProvider.getCurrentDate().minusDays(1);
        log.info("Procesando cierre para fecha: {}", yesterday);

        procesarRachasIndividuales(yesterday);

        procesarRachasGrupales(yesterday);
    }

    private void procesarRachasIndividuales(LocalDate yesterday) {
        List<HabitoParticipante> allAccepted = habitoParticipanteRepository
                .findByEstadoInvitacion(EstadoInvitacion.ACEPTADA);

        for (HabitoParticipante hp : allAccepted) {
            Habito habito = habitoRepository.findByIdAndActivoTrue(hp.getHabitoId()).orElse(null);
            if (habito == null) {
                continue;
            }

            // Grace period: skip if evaluation date is before participant's start date
            LocalDate fechaInicioReal = hp.getFechaUnion() != null
                    ? hp.getFechaUnion().toLocalDate()
                    : habito.getFechaCreacion().toLocalDate();
            if (yesterday.isBefore(fechaInicioReal)) {
                continue;
            }

            FrecuenciaStrategy strategy = resolveStrategy(habito.getFrecuencia());

            if (!strategy.isMandatoryDay(habito, yesterday)) {
                continue;
            }

            boolean completedYesterday = registroCumplimientoRepository
                    .existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(hp.getId(), yesterday);

            if (!completedYesterday) {
                log.info("Reseteando racha individual para HabitoParticipante ID: {} (habito: {})",
                        hp.getId(), habito.getNombre());
                hp.setRachaActual(0);
                habitoParticipanteRepository.save(hp);
            }
        }
    }

    private void procesarRachasGrupales(LocalDate yesterday) {
        List<Habito> allActiveHabits = habitoRepository.findByActivoTrue();

        for (Habito habito : allActiveHabits) {
            // Grace period: skip if evaluation date is before habit creation
            LocalDate fechaInicioReal = habito.getFechaCreacion().toLocalDate();
            if (yesterday.isBefore(fechaInicioReal)) {
                continue;
            }

            FrecuenciaStrategy strategy = resolveStrategy(habito.getFrecuencia());

            if (!strategy.isMandatoryDay(habito, yesterday)) {
                continue;
            }

            boolean groupCompletedYesterday = cumplimientoDiarioHabitoRepository
                    .existsByHabitoIdAndFechaAndCompletaronTrue(habito.getId(), yesterday);

            if (!groupCompletedYesterday) {
                log.info("Reseteando racha grupal para Habito ID: {} ({})",
                        habito.getId(), habito.getNombre());
                habito.setRachaGrupalActual(0);
                habitoRepository.save(habito);
            }
        }
    }
}
