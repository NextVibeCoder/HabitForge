package com.example.habitforge.ui.repository

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
}