package com.example.habitforge.ui.model

import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import java.time.LocalDate

data class Habito(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val frecuencia: FrecuenciaTipo,
    val diasSemana: List<DiaSemana>,
    val compartido: Boolean,
    val activo: Boolean,
    val fechaCreacion: String,

    )
