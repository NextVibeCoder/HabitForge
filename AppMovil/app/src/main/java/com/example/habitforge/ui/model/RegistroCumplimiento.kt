package com.example.habitforge.ui.model

import java.time.LocalDate
import java.time.LocalDateTime

data class RegistroCumplimiento(
    val id: Long,
    val fecha: LocalDate,
    val completado: Boolean,
    val xpGanado: Int,
    val fechaRegistro: String
)
