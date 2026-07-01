package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.dto.ErrorResponse
import com.example.habitforge.ui.model.dto.HabitoRequest
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.HabitoService
import com.google.gson.Gson
import retrofit2.HttpException

class HabitoRepository(
    private val habitoApiService: HabitoService
) {
    private fun parseError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.mensaje
        } catch (ex: Exception) {
            "Error inesperado: ${e.message()}"
        }
    }

    suspend fun crearHabito(
        nombre: String,
        descripcion: String,
        frecuencia: FrecuenciaTipo,
        icon: String,
        esCompartido: Boolean = false,
        diasSemana: List<DiaSemana> = emptyList(),
        amigosInvitados: List<String> = emptyList()
    ): ApiResult<Habito> {
        return try {
            ApiResult.Success(
                habitoApiService.crearHabito(
                    HabitoRequest(nombre, descripcion, frecuencia, icon, esCompartido, diasSemana, amigosInvitados)
                )
            )
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al crear hábito")
        }
    }

    suspend fun editarHabito(
        id: Long,
        nombre: String,
        descripcion: String,
        frecuencia: FrecuenciaTipo,
        icon: String,
        esCompartido: Boolean = false,
        diasSemana: List<DiaSemana> = emptyList(),
        amigosInvitados: List<String> = emptyList()
    ): ApiResult<Habito> {
        return try {
            ApiResult.Success(
                habitoApiService.editarHabito(
                    id, HabitoRequest(nombre, descripcion, frecuencia, icon, esCompartido, diasSemana, amigosInvitados)
                )
            )
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al editar hábito")
        }
    }

    suspend fun eliminarHabito(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.eliminarHabito(id))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al eliminar hábito")
        }
    }

    suspend fun obtenerHabitoById(id: Long): ApiResult<Habito> {
        return try {
            ApiResult.Success(habitoApiService.getHabitoById(id))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábito")
        }
    }

    suspend fun obtenerHabitosIndividuales(): ApiResult<List<Habito>> {
        return try {
            ApiResult.Success(habitoApiService.getHabitosIndividuales())
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábitos individuales")
        }
    }

    suspend fun obtenerHabitosCompartidos(): ApiResult<List<Habito>> {
        return try {
            ApiResult.Success(habitoApiService.getHabitosCompartidos())
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábitos compartidos")
        }
    }

    suspend fun obtenerInvitacionesPendientes(): ApiResult<List<Habito>> {
        return try {
            ApiResult.Success(habitoApiService.getInvitacionesPendientes())
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener invitaciones")
        }
    }

    suspend fun aceptarInvitacion(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.aceptarInvitacion(id))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al aceptar invitación")
        }
    }

    suspend fun rechazarInvitacion(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.rechazarInvitacion(id))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al rechazar invitación")
        }
    }

    suspend fun invitarAmigos(habitoId: Long, emails: List<String>): ApiResult<Habito> {
        return try {
            ApiResult.Success(habitoApiService.invitarAmigos(habitoId, emails))
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al invitar amigos")
        }
    }
}
