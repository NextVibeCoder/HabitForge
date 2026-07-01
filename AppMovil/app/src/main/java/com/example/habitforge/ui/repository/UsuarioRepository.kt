package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.HistorialNivel
import com.example.habitforge.ui.model.Usuario
import com.example.habitforge.ui.model.dto.profileResponse
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.UsuarioService

class UsuarioRepository(
    private val usuarioApiService: UsuarioService
) {

    suspend fun obtenerPerfil(): ApiResult<profileResponse> {
        return try {
            ApiResult.Success(usuarioApiService.getPerfil())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener perfil")
        }
    }

    suspend fun obtenerHistorialNiveles(): ApiResult<List<HistorialNivel>> {
        return try {
            ApiResult.Success(usuarioApiService.getHistorialNiveles())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener historial de niveles")
        }
    }
}