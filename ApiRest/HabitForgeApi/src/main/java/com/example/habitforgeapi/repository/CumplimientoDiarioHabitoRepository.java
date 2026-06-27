package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.CumplimientoDiarioHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CumplimientoDiarioHabitoRepository extends JpaRepository<CumplimientoDiarioHabito, Long> {

    Optional<CumplimientoDiarioHabito> findByHabitoIdAndFecha(Long habitoId, LocalDate fecha);

    boolean existsByHabitoIdAndFechaAndCompletaronTrue(Long habitoId, LocalDate fecha);
}
