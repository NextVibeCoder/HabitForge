package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.dto.AuthResponse
import com.example.habitforge.ui.model.dto.LoginRequest
import com.example.habitforge.ui.model.dto.RegistroRequest
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.AuthService
import com.example.habitforge.ui.service.SessionManager
import okhttp3.ResponseBody

class AuthRepository (
    private val authService: AuthService,
    private val sessionManager: SessionManager
) {

    suspend fun login(email: String, password: String): ApiResult<AuthResponse> {
        return try {
            val response = authService.login(LoginRequest(email, password))
            sessionManager.guardarToken(response.token)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    suspend fun registrar(username: String, email: String, password: String): ApiResult<ResponseBody> {
        return try {
            val mensaje = authService.registrar(RegistroRequest(username, email, password))
            ApiResult.Success(mensaje)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al registrar usuario")
        }
    }

    suspend fun logout(): ApiResult<Unit> {
        return try {
            authService.logout()
            sessionManager.limpiarSesion()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al cerrar sesión")
        }
    }

    fun haySesionActiva(): Boolean = sessionManager.obtenerToken() != null
}