package com.example.habitforge.ui.model.dto

import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo

data class HabitoRequest(
    val nombre: String,
    val descripcion: String?,
    val frecuencia: FrecuenciaTipo,
    val diasSemana: Set<DiaSemana> = emptySet()
)