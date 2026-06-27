package com.example.habitforge.ui.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.23:8181/api/"

    private fun buildClient(sessionManager: SessionManager): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = sessionManager.obtenerToken()

                val request = if (token != null && !originalRequest.url.encodedPath.contains("/usuario/")) {
                    originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    private fun buildRetrofit(sessionManager: SessionManager): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient(sessionManager))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun authApiService(sessionManager: SessionManager): AuthService =
        buildRetrofit(sessionManager).create(AuthService::class.java)

    fun usuarioApiService(sessionManager: SessionManager): UsuarioService =
        buildRetrofit(sessionManager).create(UsuarioService::class.java)

    fun habitoApiService(sessionManager: SessionManager): HabitoService =
        buildRetrofit(sessionManager).create(HabitoService::class.java)

    fun invitacionApiService(sessionManager: SessionManager): InvitacionService =
        buildRetrofit(sessionManager).create(InvitacionService::class.java)
}