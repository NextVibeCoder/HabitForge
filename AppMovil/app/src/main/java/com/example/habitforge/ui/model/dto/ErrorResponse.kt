package com.example.habitforge.ui.model.dto

data class ErrorResponse(
    val mensaje: String,
    val status: Int,
    val errorDetails: String? = null,
    val timestamp: String? = null
)
