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
    // Cache de sesión para mantener el estado de completado entre pantallas
    private val completedHabitsCache = mutableSetOf<Long>()

    fun marcarComoCompletadoLocalmente(id: Long) {
        completedHabitsCache.add(id)
    }

    private fun aplicarCache(habitos: List<Habito>): List<Habito> {
        return habitos.map { habito ->
            if (completedHabitsCache.contains(habito.id)) {
                habito.copy(completadoHoy = true)
            } else {
                habito
            }
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
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al editar hábito")
        }
    }

    suspend fun eliminarHabito(id: Long): ApiResult<Unit> {
        return try {
            completedHabitsCache.remove(id)
            ApiResult.Success(habitoApiService.eliminarHabito(id))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al eliminar hábito")
        }
    }

    suspend fun obtenerHabitoById(id: Long): ApiResult<Habito> {
        return try {
            val habito = habitoApiService.getHabitoById(id)
            ApiResult.Success(if (completedHabitsCache.contains(habito.id)) habito.copy(completadoHoy = true) else habito)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábito")
        }
    }

    suspend fun obtenerHabitosIndividuales(): ApiResult<List<Habito>> {
        return try {
            val habitos = habitoApiService.getHabitosIndividuales()
            ApiResult.Success(aplicarCache(habitos))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábitos individuales")
        }
    }

    suspend fun obtenerHabitosCompartidos(): ApiResult<List<Habito>> {
        return try {
            val habitos = habitoApiService.getHabitosCompartidos()
            ApiResult.Success(aplicarCache(habitos))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener hábitos compartidos")
        }
    }

    suspend fun obtenerInvitacionesPendientes(): ApiResult<List<Habito>> {
        return try {
            ApiResult.Success(habitoApiService.getInvitacionesPendientes())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener invitaciones")
        }
    }

    suspend fun aceptarInvitacion(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.aceptarInvitacion(id))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al aceptar invitación")
        }
    }

    suspend fun rechazarInvitacion(id: Long): ApiResult<Unit> {
        return try {
            ApiResult.Success(habitoApiService.rechazarInvitacion(id))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al rechazar invitación")
        }
    }

    suspend fun invitarAmigos(habitoId: Long, emails: List<String>): ApiResult<Habito> {
        return try {
            ApiResult.Success(habitoApiService.invitarAmigos(habitoId, emails))
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al invitar amigos")
        }
    }
}