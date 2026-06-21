package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.RegistroCumplimiento
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.service.HabitoService

class HabitoRepository(
    private val habitoService: HabitoService
) {

    suspend fun obtenerHabitos(): Result<List<Habito>> {
        return runCatching { habitoService.getHabitos() }
    }

    suspend fun crearHabito(
        nombre: String,
        descripcion: String?,
        frecuencia: FrecuenciaTipo,
        diasSemana: Set<DiaSemana> = emptySet()
    ): Result<Habito> {
        return runCatching {
            habitoService.crearHabito(
                HabitoRequest(nombre, descripcion, frecuencia, diasSemana)
            )
        }
    }

    suspend fun editarHabito(
        id: Long,
        nombre: String,
        descripcion: String?,
        frecuencia: FrecuenciaTipo,
        diasSemana: Set<DiaSemana> = emptySet()
    ): Result<Habito> {
        return runCatching {
            habitoService.editarHabito(
                id, HabitoRequest(nombre, descripcion, frecuencia, diasSemana)
            )
        }
    }

    suspend fun eliminarHabito(id: Long): Result<Unit> {
        return runCatching { habitoService.eliminarHabito(id) }
    }

    suspend fun completarHabito(
        habitoId: Long,
        fecha: String
    ): Result<RegistroCumplimiento> {
        return runCatching {
            habitoService.completarHabito(habitoId, fecha)
        }
    }

    suspend fun obtenerCalendario(
        habitoId: Long,
        anio: Int,
        mes: Int
    ): Result<List<RegistroCumplimiento>> {
        return runCatching {
            habitoService.getCalendario(habitoId, anio, mes)
        }
    }
}