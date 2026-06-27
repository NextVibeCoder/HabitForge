package com.example.habitforgeapi.dto;

import com.example.habitforgeapi.model.DiaSemana;
import com.example.habitforgeapi.model.Frecuencia;
import java.time.LocalDateTime;
import java.util.List;

public class HabitoResponseDTO {
    private Long id;
    private Long creadorId;
    private String nombre;
    private String descripcion;
    private Frecuencia frecuencia;
    private String icon;
    private boolean esCompartido;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private int rachaGrupalActual;
    private int rachaGrupalMasLarga;
    private List<DiaSemana> diasSemana;
    private List<HabitoParticipanteResponseDTO> participantes;

    public HabitoResponseDTO() {
    }

    public HabitoResponseDTO(Long id, Long creadorId, String nombre, String descripcion, Frecuencia frecuencia,
                              String icon, boolean esCompartido, boolean activo, LocalDateTime fechaCreacion,
                              int rachaGrupalActual, int rachaGrupalMasLarga,
                              List<DiaSemana> diasSemana, List<HabitoParticipanteResponseDTO> participantes) {
        this.id = id;
        this.creadorId = creadorId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
        this.icon = icon;
        this.esCompartido = esCompartido;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.rachaGrupalActual = rachaGrupalActual;
        this.rachaGrupalMasLarga = rachaGrupalMasLarga;
        this.diasSemana = diasSemana;
        this.participantes = participantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(Long creadorId) {
        this.creadorId = creadorId;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<HabitoParticipanteResponseDTO> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<HabitoParticipanteResponseDTO> participantes) {
        this.participantes = participantes;
    }

    public int getRachaGrupalActual() {
        return rachaGrupalActual;
    }

    public void setRachaGrupalActual(int rachaGrupalActual) {
        this.rachaGrupalActual = rachaGrupalActual;
    }

    public int getRachaGrupalMasLarga() {
        return rachaGrupalMasLarga;
    }

    public void setRachaGrupalMasLarga(int rachaGrupalMasLarga) {
        this.rachaGrupalMasLarga = rachaGrupalMasLarga;
    }
}
