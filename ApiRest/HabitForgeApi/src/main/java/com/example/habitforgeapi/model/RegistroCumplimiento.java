package com.example.habitforgeapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "registro_cumplimiento",
    uniqueConstraints = @UniqueConstraint(columnNames = {"habito_participante_id", "fecha"})
)
@AttributeOverride(name = "id", column = @Column(name = "registro_cumplimiento_id"))
public class RegistroCumplimiento extends BaseEntity {

    @Column(name = "habito_participante_id", nullable = false)
    private Long habitoParticipanteId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "completado", nullable = false)
    private boolean completado;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    public RegistroCumplimiento() {
    }

    public RegistroCumplimiento(Long habitoParticipanteId, LocalDate fecha, boolean completado) {
        this.habitoParticipanteId = habitoParticipanteId;
        this.fecha = fecha;
        this.completado = completado;
        this.fechaRegistro = LocalDateTime.now();
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
}
