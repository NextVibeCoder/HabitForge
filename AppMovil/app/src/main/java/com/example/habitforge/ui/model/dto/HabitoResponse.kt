package com.example.habitforge.ui.model.dto

import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo

data class HabitoResponse(
    val id: Long,
    val creadorId: Long,
    val nombre: String,
    val descripcion: String,
    val frecuencia: FrecuenciaTipo,
    val icon: String,
    val esCompartido: Boolean,
    val activo: Boolean,
    val fechaCreacion: String,
    val rachaGrupalActual: Int,
    val rachaGrupalMasLarga: Int,
    val diasSemana: List<DiaSemana>,
    val participantes: List<HabitoParticipanteResponse>
)
