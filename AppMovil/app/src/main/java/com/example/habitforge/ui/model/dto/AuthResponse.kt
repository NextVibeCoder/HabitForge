package com.example.habitforge.ui.model.dto

data class AuthResponse(
    val token: String,
    val usuarioId: Long,
    val username: String
)