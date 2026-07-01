package com.example.habitforge.ui.model.dto

data class profileResponse(
    val username: String,
    val email: String,
    val rachaMasLarga: Int,
    val cantidadHabitosActivos: Int,
    val fechaRegistro: String,
    val historial: List<HistorialCumplimiento>
)
