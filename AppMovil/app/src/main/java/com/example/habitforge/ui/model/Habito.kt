package com.example.habitforge.ui.model

import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.dto.HabitoParticipanteResponse

data class Habito(
    val id: Long,
    val creadorId: Long,
    val nombre: String,
    val descripcion: String,
    val frecuencia: FrecuenciaTipo,
    val icon: String,
    val esCompartido: Boolean,
    val activo: Boolean,
    val rachaGrupalActual: Int,
    val rachaGrupalMasLarga: Int,
    val fechaCreacion: String,
    val esDiaObligatorio: Boolean = true,
    val diasSemana: List<DiaSemana> = emptyList(),
    val participantes: List<HabitoParticipanteResponse> = emptyList()
)
