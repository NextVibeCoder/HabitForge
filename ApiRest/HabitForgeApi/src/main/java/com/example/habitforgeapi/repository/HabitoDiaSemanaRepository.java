package com.example.habitforgeapi.repository;

import com.example.habitforgeapi.model.HabitoDiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoDiaSemanaRepository extends JpaRepository<HabitoDiaSemana, Long> {
    List<HabitoDiaSemana> findByHabitoId(Long habitoId);

    @Modifying
    @Transactional
    void deleteByHabitoId(Long habitoId);
}
