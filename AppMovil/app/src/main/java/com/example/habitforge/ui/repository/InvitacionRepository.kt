package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.HabitoParticipante
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.InvitacionService

class InvitacionRepository(
    private val invitacionApiService: InvitacionService
) {

    suspend fun invitarAmigo(
        habitoId: Long,
        usernameOEmail: String
    ): ApiResult<HabitoParticipante> {
        return try {
            ApiResult.Success(invitacionApiService.invitar(habitoId, usernameOEmail))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al enviar invitación")
        }
    }

    suspend fun aceptarInvitacion(participanteId: Long): ApiResult<HabitoParticipante> {
        return try {
            ApiResult.Success(invitacionApiService.aceptar(participanteId))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al aceptar invitación")
        }
    }

    suspend fun rechazarInvitacion(participanteId: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(invitacionApiService.rechazar(participanteId))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al rechazar invitación")
        }
    }

    suspend fun obtenerInvitacionesPendientes(): ApiResult<List<HabitoParticipante>> {
        return try {
            ApiResult.Success(invitacionApiService.getPendientes())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener invitaciones")
        }
    }

    suspend fun obtenerParticipantes(habitoId: Long): ApiResult<List<HabitoParticipante>> {
        return try {
            ApiResult.Success(invitacionApiService.getParticipantes(habitoId))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener participantes")
        }
    }
}