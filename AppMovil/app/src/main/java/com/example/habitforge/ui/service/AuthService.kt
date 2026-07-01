package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.dto.AuthResponse
import com.example.habitforge.ui.model.dto.LoginRequest
import com.example.habitforge.ui.model.dto.RegistroRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("usuario/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("usuario/registro")
    suspend fun registrar(@Body request: RegistroRequest): ResponseBody
}