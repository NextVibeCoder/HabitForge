package com.example.habitforgeapi.dto;

import com.example.habitforgeapi.model.DiaSemana;
import com.example.habitforgeapi.model.Frecuencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class HabitoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "La frecuencia es obligatoria")
    private Frecuencia frecuencia;

    @NotBlank(message = "El ícono es obligatorio")
    private String icon;

    private boolean esCompartido;

    private List<DiaSemana> diasSemana;

    private List<String> amigosInvitados;

    public HabitoRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isEsCompartido() {
        return esCompartido;
    }

    public void setEsCompartido(boolean esCompartido) {
        this.esCompartido = esCompartido;
    }

    public List<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<String> getAmigosInvitados() {
        return amigosInvitados;
    }

    public void setAmigosInvitados(List<String> amigosInvitados) {
        this.amigosInvitados = amigosInvitados;
    }
}
