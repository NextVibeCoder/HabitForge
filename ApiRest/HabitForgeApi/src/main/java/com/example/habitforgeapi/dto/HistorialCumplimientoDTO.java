package com.example.habitforgeapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistorialCumplimientoDTO {
    private String nombreHabito;
    private LocalDate fecha;
    private LocalDateTime fechaRegistro;

    public HistorialCumplimientoDTO() {
    }

    public HistorialCumplimientoDTO(String nombreHabito, LocalDate fecha, LocalDateTime fechaRegistro) {
        this.nombreHabito = nombreHabito;
        this.fecha = fecha;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreHabito() {
        return nombreHabito;
    }

    public void setNombreHabito(String nombreHabito) {
        this.nombreHabito = nombreHabito;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
