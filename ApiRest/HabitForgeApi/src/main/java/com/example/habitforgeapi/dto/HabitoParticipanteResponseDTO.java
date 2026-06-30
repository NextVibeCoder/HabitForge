package com.example.habitforgeapi.dto;

import com.example.habitforgeapi.model.EstadoInvitacion;
import java.time.LocalDateTime;

public class HabitoParticipanteResponseDTO {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private int rachaActual;
    private int rachaMasLarga;
    private EstadoInvitacion estadoInvitacion;
    private LocalDateTime fechaUnion;
    private boolean completadoHoy;

    public HabitoParticipanteResponseDTO() {
    }

    public HabitoParticipanteResponseDTO(Long id, LocalDateTime fechaUnion, EstadoInvitacion estadoInvitacion,
                                         int rachaMasLarga, int rachaActual, Long usuarioId, String nombreUsuario,
                                         boolean completadoHoy) {
        this.id = id;
        this.fechaUnion = fechaUnion;
        this.estadoInvitacion = estadoInvitacion;
        this.rachaMasLarga = rachaMasLarga;
        this.rachaActual = rachaActual;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.completadoHoy = completadoHoy;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public boolean isCompletadoHoy() {
        return completadoHoy;
    }

    public void setCompletadoHoy(boolean completadoHoy) {
        this.completadoHoy = completadoHoy;
    }
}
