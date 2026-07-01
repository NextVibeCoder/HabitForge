package com.example.habitforgeapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserProfileResponseDTO {
    private String username;
    private String email;
    private int rachaMasLarga;
    private int cantidadHabitosActivos;
    private LocalDateTime fechaRegistro;
    private List<HistorialCumplimientoDTO> historial;

    public UserProfileResponseDTO() {
    }

    public UserProfileResponseDTO(String username, String email, int rachaMasLarga, int cantidadHabitosActivos, LocalDateTime fechaRegistro, List<HistorialCumplimientoDTO> historial) {
        this.username = username;
        this.email = email;
        this.rachaMasLarga = rachaMasLarga;
        this.cantidadHabitosActivos = cantidadHabitosActivos;
        this.fechaRegistro = fechaRegistro;
        this.historial = historial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRachaMasLarga() {
        return rachaMasLarga;
    }

    public void setRachaMasLarga(int rachaMasLarga) {
        this.rachaMasLarga = rachaMasLarga;
    }

    public int getCantidadHabitosActivos() {
        return cantidadHabitosActivos;
    }

    public void setCantidadHabitosActivos(int cantidadHabitosActivos) {
        this.cantidadHabitosActivos = cantidadHabitosActivos;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<HistorialCumplimientoDTO> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialCumplimientoDTO> historial) {
        this.historial = historial;
    }
}
