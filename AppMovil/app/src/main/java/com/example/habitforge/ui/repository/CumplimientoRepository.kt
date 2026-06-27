package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.RegistroCumplimiento
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.CumplimientoService

class CumplimientoRepository(private val cumplimientoService: CumplimientoService) {
    suspend fun completarHabito(
        habitoId: Long,
        fecha: String
    ): ApiResult<RegistroCumplimiento> {
        return try {
            ApiResult.Success(cumplimientoService.completarHabito(habitoId, fecha))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al completar hábito")
        }
    }
}