package com.example.habitforgeapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CumplimientoResponseDTO {

    private Long id;
    private Long habitoParticipanteId;
    private LocalDate fecha;
    private boolean completado;
    private LocalDateTime fechaRegistro;
    private int rachaActual;
    private int rachaMasLarga;

    public CumplimientoResponseDTO() {
    }

    public CumplimientoResponseDTO(Long id, Long habitoParticipanteId, LocalDate fecha,
                                    boolean completado, LocalDateTime fechaRegistro,
                                    int rachaActual, int rachaMasLarga) {
        this.id = id;
        this.habitoParticipanteId = habitoParticipanteId;
        this.fecha = fecha;
        this.completado = completado;
        this.fechaRegistro = fechaRegistro;
        this.rachaActual = rachaActual;
        this.rachaMasLarga = rachaMasLarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHabitoParticipanteId() {
        return habitoParticipanteId;
    }

    public void setHabitoParticipanteId(Long habitoParticipanteId) {
        this.habitoParticipanteId = habitoParticipanteId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getRachaMasLarga() {
        return rachaMasLarga;
    }

    public void setRachaMasLarga(int rachaMasLarga) {
        this.rachaMasLarga = rachaMasLarga;
    }
}
