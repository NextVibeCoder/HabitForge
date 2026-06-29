package com.example.habitforge.ui.model.dto

data class CumplimientoResponse (
    val id: Long,
    val habitoParticipanteId: Long,
    val fecha: String,
    val completado: Boolean,
    val fechaRegistro: String,
    val rachaActual: Int,
    val rachaMasLarga: Int
)
