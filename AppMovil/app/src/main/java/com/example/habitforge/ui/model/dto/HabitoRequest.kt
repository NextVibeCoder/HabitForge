package com.example.habitforge.ui.model.dto

import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo

data class HabitoRequest(
    val nombre: String,
    val descripcion: String,
    val frecuencia: FrecuenciaTipo,
    val icon: String,
    val esCompartido: Boolean,
    val diasSemana: List<DiaSemana> = emptyList(),
    val amigosInvitados: List<String> = emptyList()
)