package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
    Optional<Habito> findByIdAndActivoTrue(Long id);
    List<Habito> findByCreadorIdAndEsCompartidoFalseAndActivoTrue(Long creadorId);
}
