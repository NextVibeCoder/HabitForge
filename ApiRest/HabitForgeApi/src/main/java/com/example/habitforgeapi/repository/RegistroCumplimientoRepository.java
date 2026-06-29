package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.dto.HistorialCumplimientoDTO;
import com.example.habitforgeapi.model.RegistroCumplimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroCumplimientoRepository extends JpaRepository<RegistroCumplimiento, Long> {

    Optional<RegistroCumplimiento> findByHabitoParticipanteIdAndFecha(Long habitoParticipanteId, LocalDate fecha);

    boolean existsByHabitoParticipanteIdAndFechaAndCompletadoTrue(Long habitoParticipanteId, LocalDate fecha);

    @Query("SELECT new com.example.habitforgeapi.dto.HistorialCumplimientoDTO(h.nombre, rc.fecha, rc.fechaRegistro) " +
           "FROM RegistroCumplimiento rc " +
           "JOIN HabitoParticipante hp ON rc.habitoParticipanteId = hp.id " +
           "JOIN Habito h ON hp.habitoId = h.id " +
           "WHERE hp.usuarioId = :usuarioId AND rc.completado = true " +
           "ORDER BY rc.fecha DESC")
    List<HistorialCumplimientoDTO> findHistorialByUsuarioId(@Param("usuarioId") Long usuarioId);
}
