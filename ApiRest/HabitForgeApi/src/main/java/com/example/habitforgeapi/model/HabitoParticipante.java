package com.example.habitforgeapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "habito_participante")
@AttributeOverride(name = "id", column = @Column(name = "habito_participante_id"))
public class HabitoParticipante extends BaseEntity {

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "habito_id", nullable = false)
    private Long habitoId;

    @Column(name = "racha_actual", nullable = false)
    private int rachaActual = 0;

    @Column(name = "racha_mas_larga", nullable = false)
    private int rachaMasLarga = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_invitacion", nullable = false)
    private EstadoInvitacion estadoInvitacion;

    @Column(name = "fecha_union")
    private LocalDateTime fechaUnion;

    public HabitoParticipante() {
    }

    public HabitoParticipante(Long usuarioId, Long habitoId, EstadoInvitacion estadoInvitacion) {
        this.usuarioId = usuarioId;
        this.habitoId = habitoId;
        this.estadoInvitacion = estadoInvitacion;
        this.rachaActual = 0;
        this.rachaMasLarga = 0;
        if (estadoInvitacion == EstadoInvitacion.ACEPTADA) {
            this.fechaUnion = LocalDateTime.now();
        } else {
            this.fechaUnion = null;
        }
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getHabitoId() {
        return habitoId;
    }

    public void setHabitoId(Long habitoId) {
        this.habitoId = habitoId;
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
