package com.example.habitforge.ui.service

import android.content.Context
import com.example.habitforge.ui.repository.AuthRepository
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.repository.UsuarioRepository
import com.example.habitforge.ui.repository.CumplimientoRepository

object ServiceLocator {

    private lateinit var sessionManager: SessionManager

    private val authRepository by lazy {
        AuthRepository(
            RetrofitClient.authApiService(sessionManager),
            sessionManager
        )
    }

    private val cumplimientoRepository by lazy {
        CumplimientoRepository(RetrofitClient.cumplimientoRepository(sessionManager))
    }
    private val usuarioRepository by lazy {
        UsuarioRepository(RetrofitClient.usuarioApiService(sessionManager))
    }

    private val habitoRepository by lazy {
        HabitoRepository(RetrofitClient.habitoApiService(sessionManager))
    }

    fun init(context: Context) {
        sessionManager = SessionManager(context.applicationContext)
    }

    fun provideAuthRepository(): AuthRepository = authRepository
    fun provideUsuarioRepository(): UsuarioRepository = usuarioRepository
    fun provideCumplimientoRepository(): CumplimientoRepository = cumplimientoRepository
    fun provideHabitoRepository(): HabitoRepository = habitoRepository
}