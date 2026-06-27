package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.EstadoInvitacion;
import com.example.habitforgeapi.model.HabitoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
