package com.example.habitforge.ui.model

import java.time.LocalDate

data class CumplimientoDiarioHabito(
    val id: Long,
    val fecha: String,
    val todosCompletaron: Boolean,
    val participantesCompletados: Int
)
