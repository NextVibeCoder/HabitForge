package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.dto.profileResponse
import retrofit2.http.GET

interface UsuarioService {

    @GET("usuario/perfil")
    suspend fun getPerfil(): profileResponse
}