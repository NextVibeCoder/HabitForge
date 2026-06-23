package com.example.habitforge


import com.example.habitforge.ui.model.dto.LoginRequest
import com.example.habitforge.ui.model.dto.RegistroRequest
import com.example.habitforge.ui.service.AuthService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExampleUnitTest {

    private val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8181/api/")
            .client(OkHttpClient())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
            .create(AuthService::class.java)
    }

    @Test
    fun testRegistrarUsuario() = runBlocking {
        val response = authService.registrar(
            RegistroRequest(
                username = "TestUser5",
                email = "testuser5@gmail.com",
                password = "test1234"
            )
        ).string()
        println("Respuesta registro: $response")
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun testLoginUsuario() = runBlocking {
        val response = authService.login(
            LoginRequest(
                email = "testuser4@gmail.com",
                password = "test1234"
            )
        )
        println("Token recibido: ${response.token}")
        assertNotNull(response.token)
        assertTrue(response.token.isNotEmpty())
    }
}