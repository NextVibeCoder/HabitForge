package com.example.habitforge.ui.model

data class RegistroCumplimiento(
    val id: Long,
    val fecha: String,
    val completado: Boolean,
    val xpGanado: Int,
    val fechaRegistro: String
)
