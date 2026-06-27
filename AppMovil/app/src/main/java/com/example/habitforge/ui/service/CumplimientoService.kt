package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.RegistroCumplimiento
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CumplimientoService {

    @POST("v1/cumplimientos")
    suspend fun completarHabito(
        @Path("id") id: Long,
        @Query("fecha") fecha: String
    ): RegistroCumplimiento
}