package com.example.habitforgeapi.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "habito_dia_semana",
    uniqueConstraints = @UniqueConstraint(columnNames = {"habito_id", "dia_semana"})
)
@AttributeOverride(name = "id", column = @Column(name = "habito_dia_semana_id"))
public class HabitoDiaSemana extends BaseEntity {

    @Column(name = "habito_id", nullable = false)
    private Long habitoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    public HabitoDiaSemana() {
    }

    public HabitoDiaSemana(Long habitoId, DiaSemana diaSemana) {
        this.habitoId = habitoId;
        this.diaSemana = diaSemana;
    }

    public Long getHabitoId() {
        return habitoId;
    }

    public void setHabitoId(Long habitoId) {
        this.habitoId = habitoId;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }
}
