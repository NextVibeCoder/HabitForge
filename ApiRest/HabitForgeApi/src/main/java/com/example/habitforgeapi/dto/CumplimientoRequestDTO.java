package com.example.habitforgeapi.dto;

import jakarta.validation.constraints.NotNull;

public class CumplimientoRequestDTO {

    @NotNull(message = "El ID del hábito es obligatorio")
    private Long habitoId;

    public CumplimientoRequestDTO() {
    }

    public CumplimientoRequestDTO(Long habitoId) {
        this.habitoId = habitoId;
    }

    public Long getHabitoId() {
        return habitoId;
    }

    public void setHabitoId(Long habitoId) {
        this.habitoId = habitoId;
    }
}
