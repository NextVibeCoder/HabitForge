package com.example.habitforgeapi.dto;

import com.example.habitforgeapi.model.EstadoInvitacion;
import java.time.LocalDateTime;

public class HabitoParticipanteResponseDTO {
    private Long id;
    private Long usuarioId;
    private int rachaActual;
    private int rachaMasLarga;
    private EstadoInvitacion estadoInvitacion;
    private LocalDateTime fechaUnion;

    public HabitoParticipanteResponseDTO() {
    }

    public HabitoParticipanteResponseDTO(Long id, Long usuarioId, int rachaActual, int rachaMasLarga, EstadoInvitacion estadoInvitacion, LocalDateTime fechaUnion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.rachaActual = rachaActual;
        this.rachaMasLarga = rachaMasLarga;
        this.estadoInvitacion = estadoInvitacion;
        this.fechaUnion = fechaUnion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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

    public EstadoInvitacion getEstadoInvitacion() {
        return estadoInvitacion;
    }

    public void setEstadoInvitacion(EstadoInvitacion estadoInvitacion) {
        this.estadoInvitacion = estadoInvitacion;
    }

    public LocalDateTime getFechaUnion() {
        return fechaUnion;
    }

    public void setFechaUnion(LocalDateTime fechaUnion) {
        this.fechaUnion = fechaUnion;
    }
}
