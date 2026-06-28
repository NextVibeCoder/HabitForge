package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.RegistroCumplimiento
import com.example.habitforge.ui.model.dto.CumplimientoRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CumplimientoService {

    @POST("v1/cumplimientos")
    suspend fun registrarCumplimiento(@Body request: CumplimientoRequest): RegistroCumplimiento

}