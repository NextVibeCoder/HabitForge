package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.RegistroCumplimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RegistroCumplimientoRepository extends JpaRepository<RegistroCumplimiento, Long> {

    Optional<RegistroCumplimiento> findByHabitoParticipanteIdAndFecha(Long habitoParticipanteId, LocalDate fecha);

    boolean existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(Long habitoParticipanteId, LocalDate fecha);
}
