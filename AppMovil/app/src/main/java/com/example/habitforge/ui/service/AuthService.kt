package com.example.habitforge.ui.service

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/registro")
    suspend fun registrar(@Body request: RegistroRequest): AuthResponse

    @POST("auth/logout")
    suspend fun logout()
}