package com.example.habitforge.ui.model

import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import java.time.LocalDate

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
    )
