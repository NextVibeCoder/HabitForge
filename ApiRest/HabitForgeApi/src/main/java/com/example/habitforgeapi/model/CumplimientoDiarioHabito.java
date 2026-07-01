package com.example.habitforgeapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "cumplimiento_diario_habito",
    uniqueConstraints = @UniqueConstraint(columnNames = {"habito_id", "fecha"})
)
@AttributeOverride(name = "id", column = @Column(name = "cumplimiento_diario_id"))
public class CumplimientoDiarioHabito extends BaseEntity {

    @Column(name = "habito_id", nullable = false)
    private Long habitoId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "completaron", nullable = false)
    private boolean completaron;

    public CumplimientoDiarioHabito() {
    }

    public CumplimientoDiarioHabito(Long habitoId, LocalDate fecha, boolean completaron) {
        this.habitoId = habitoId;
        this.fecha = fecha;
        this.completaron = completaron;
    }

    public Long getHabitoId() {
        return habitoId;
    }

    public void setHabitoId(Long habitoId) {
        this.habitoId = habitoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletaron() {
        return completaron;
    }

    public void setCompletaron(boolean completaron) {
        this.completaron = completaron;
    }
}
