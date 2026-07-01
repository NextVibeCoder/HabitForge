package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.RegistroCumplimiento
import com.example.habitforge.ui.model.dto.CumplimientoRequest
import com.example.habitforge.ui.model.dto.ErrorResponse
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.CumplimientoService
import com.google.gson.Gson
import retrofit2.HttpException

class CumplimientoRepository(private val cumplimientoService: CumplimientoService) {
    private fun parseError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.mensaje
        } catch (ex: Exception) {
            "Error inesperado: ${e.message()}"
        }
    }

    suspend fun cumplimiento(
        habitoId: Long
    ): ApiResult<RegistroCumplimiento> {
        return try {
            ApiResult.Success(cumplimientoService.registrarCumplimiento(CumplimientoRequest(habitoId)))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al completar hábito")
        }
    }
}
