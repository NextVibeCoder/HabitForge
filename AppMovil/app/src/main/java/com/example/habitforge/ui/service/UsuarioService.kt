package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.HistorialNivel
import com.example.habitforge.ui.model.Usuario
import retrofit2.http.GET

interface UsuarioService {

    @GET("usuario/perfil")
    suspend fun getPerfil(): Usuario

    @GET("usuarios/historial-niveles")
    suspend fun getHistorialNiveles(): List<HistorialNivel>
}