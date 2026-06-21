package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.HabitoParticipante
import com.example.habitforge.ui.service.InvitacionService

class InvitacionRepository(
    private val invitacionService: InvitacionService
) {

    suspend fun invitarAmigo(
        habitoId: Long,
        usernameOEmail: String
    ): Result<HabitoParticipante> {
        return runCatching {
            invitacionService.invitar(habitoId, usernameOEmail)
        }
    }

    suspend fun aceptarInvitacion(participanteId: Long): Result<HabitoParticipante> {
        return runCatching { invitacionService.aceptar(participanteId) }
    }

    suspend fun rechazarInvitacion(participanteId: Long): Result<Unit> {
        return runCatching { invitacionService.rechazar(participanteId) }
    }

    suspend fun obtenerInvitacionesPendientes(): Result<List<HabitoParticipante>> {
        return runCatching { invitacionService.getPendientes() }
    }

    suspend fun obtenerParticipantes(habitoId: Long): Result<List<HabitoParticipante>> {
        return runCatching { invitacionService.getParticipantes(habitoId) }
    }
}