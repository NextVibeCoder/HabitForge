package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.dto.HabitoRequest
import com.example.habitforge.ui.model.RegistroCumplimiento
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.HabitoService

class HabitoRepository(
    private val habitoApiService: HabitoService
) {

    suspend fun obtenerHabitos(): ApiResult<List<Habito>> {
        return try {
            ApiResult.Success(habitoApiService.getHabitos())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábitos")
        }
    }

    suspend fun crearHabito(
        nombre: String,
        descripcion: String?,
        frecuencia: FrecuenciaTipo,
        diasSemana: Set<DiaSemana> = emptySet()
    ): ApiResult<Habito> {
        return try {
            ApiResult.Success(
                habitoApiService.crearHabito(
                    HabitoRequest(nombre, descripcion, frecuencia, diasSemana)
                )
            )
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al crear hábito")
        }
    }

    suspend fun editarHabito(
        id: Long,
        nombre: String,
        descripcion: String?,
        frecuencia: FrecuenciaTipo,
        diasSemana: Set<DiaSemana> = emptySet()
    ): ApiResult<Habito> {
        return try {
            ApiResult.Success(
                habitoApiService.editarHabito(
                    id, HabitoRequest(nombre, descripcion, frecuencia, diasSemana)
                )
            )
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al editar hábito")
        }
    }

    suspend fun eliminarHabito(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.eliminarHabito(id))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al eliminar hábito")
        }
    }

    suspend fun completarHabito(
        habitoId: Long,
        fecha: String
    ): ApiResult<RegistroCumplimiento> {
        return try {
            ApiResult.Success(habitoApiService.completarHabito(habitoId, fecha))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al completar hábito")
        }
    }

    suspend fun obtenerCalendario(
        habitoId: Long,
        anio: Int,
        mes: Int
    ): ApiResult<List<RegistroCumplimiento>> {
        return try {
            ApiResult.Success(habitoApiService.getCalendario(habitoId, anio, mes))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener calendario")
        }
    }
}