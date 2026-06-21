package com.example.habitforge.ui.repository

import com.example.habitforge.ui.service.AuthService
import com.example.habitforge.ui.service.SessionManager

class AuthRepository (private val authService: AuthService,
                      private val sessionManager: SessionManager
) {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return runCatching {
            val response = authService.login(LoginRequest(email, password))
            sessionManager.guardarToken(response.token)
            sessionManager.guardarUsuarioId(response.usuarioId)
            response
        }
    }

    suspend fun registrar(
        username: String,
        email: String,
        password: String
    ): Result<AuthResponse> {
        return runCatching {
            val response = authService.registrar(
                RegistroRequest(username, email, password)
            )
            sessionManager.guardarToken(response.token)
            sessionManager.guardarUsuarioId(response.usuarioId)
            response
        }
    }

    suspend fun logout(): Result<Unit> {
        return runCatching {
            authService.logout()
            sessionManager.limpiarSesion()
        }
    }

    fun haySesionActiva(): Boolean = sessionManager.obtenerToken() != null
}
