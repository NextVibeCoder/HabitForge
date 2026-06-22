package com.example.habitforge.ui.model

import com.example.habitforge.ui.model.enums.EstadoInvitacion
import java.time.LocalDate

data class HabitoParticipante(
    val id: Long,
    val rachaActual: Int,
    val rachaMasLarga: Int,
    val estadoInvitacion: EstadoInvitacion,
    val fechaUnion: String
)
