package com.example.habitforge.ui.model.dto

import com.example.habitforge.ui.model.enums.EstadoInvitacion

data class HabitoParticipanteResponse(
    val id: Long,
    val usuarioId: Long,
    val rachaActual: Int,
    val rachaMasLarga: Int,
    val estadoInvitacion: EstadoInvitacion,
    val fechaUnion: String
)
