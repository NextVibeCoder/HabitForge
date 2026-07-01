package com.example.habitforge.ui.model

import java.time.LocalDateTime

data class Usuario(
    val id: Long,
    val username: String,
    val email: String,
    val xpTotal: Int,
    val nivelActual: Int,
    val fechaRegistro: String
)
