package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.EstadoInvitacion;
import com.example.habitforgeapi.model.HabitoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitoParticipanteRepository extends JpaRepository<HabitoParticipante, Long> {
    Optional<HabitoParticipante> findByHabitoIdAndUsuarioId(Long habitoId, Long usuarioId);
    List<HabitoParticipante> findByHabitoId(Long habitoId);
    void deleteByHabitoId(Long habitoId);

    @Query("SELECT hp FROM HabitoParticipante hp JOIN Habito h ON hp.habitoId = h.id " +
           "WHERE hp.usuarioId = :usuarioId AND hp.estadoInvitacion = :estadoInvitacion " +
           "AND h.activo = true AND h.esCompartido = true")
    List<HabitoParticipante> findSharedHabitsForUser(Long usuarioId, EstadoInvitacion estadoInvitacion);

    List<HabitoParticipante> findByEstadoInvitacion(EstadoInvitacion estadoInvitacion);

    List<HabitoParticipante> findByHabitoIdAndEstadoInvitacion(Long habitoId, EstadoInvitacion estadoInvitacion);

    @Query("SELECT COUNT(hp) FROM HabitoParticipante hp JOIN Habito h ON hp.habitoId = h.id " +
           "WHERE hp.usuarioId = :usuarioId AND hp.estadoInvitacion = com.example.habitforgeapi.model.EstadoInvitacion.ACEPTADA AND h.activo = true")
    long countActiveHabitosByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COALESCE(MAX(hp.rachaMasLarga), 0) FROM HabitoParticipante hp JOIN Habito h ON hp.habitoId = h.id " +
           "WHERE hp.usuarioId = :usuarioId AND hp.estadoInvitacion = com.example.habitforgeapi.model.EstadoInvitacion.ACEPTADA AND h.activo = true")
    int findMaxRachaMasLargaByUsuarioId(@Param("usuarioId") Long usuarioId);
}

