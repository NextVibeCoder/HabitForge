package com.example.habitforge.ui.repository

import com.example.habitforge.ui.model.dto.AuthResponse
import com.example.habitforge.ui.model.dto.ErrorResponse
import com.example.habitforge.ui.model.dto.LoginRequest
import com.example.habitforge.ui.model.dto.RegistroRequest
import com.example.habitforge.ui.service.ApiResult
import com.example.habitforge.ui.service.AuthService
import com.example.habitforge.ui.service.SessionManager
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException

class AuthRepository (
    private val authApiService: AuthService,
    private val sessionManager: SessionManager
) {

    private fun parseError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.mensaje
        } catch (ex: Exception) {
            "Error inesperado: ${e.message()}"
        }
    }

    suspend fun login(email: String, password: String): ApiResult<AuthResponse> {
        return try {
            val response = authApiService.login(LoginRequest(email, password))
            sessionManager.guardarToken(response.token)
            sessionManager.guardarUsuarioId(response.usuarioId)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    suspend fun registrar(username: String, email: String, password: String): ApiResult<ResponseBody> {
        return try {
            val mensaje = authApiService.registrar(RegistroRequest(username, email, password))
            ApiResult.Success(mensaje)
        } catch (e: HttpException) {
            ApiResult.Error(parseError(e), e.code())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al registrar usuario")
        }
    }

    fun obtenerUsuarioId(): Long? = sessionManager.obtenerUsuarioId()
}