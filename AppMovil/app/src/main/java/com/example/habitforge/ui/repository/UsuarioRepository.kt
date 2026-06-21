package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.HistorialNivel
import com.example.habitforge.ui.model.Usuario
import com.example.habitforge.ui.service.UsuarioService

class UsuarioRepository(
    private val usuarioService: UsuarioService
) {

    suspend fun obtenerPerfil(): Result<Usuario> {
        return runCatching { usuarioService.getPerfil() }
    }

    suspend fun obtenerHistorialNiveles(): Result<List<HistorialNivel>> {
        return runCatching { usuarioService.getHistorialNiveles() }
    }
}