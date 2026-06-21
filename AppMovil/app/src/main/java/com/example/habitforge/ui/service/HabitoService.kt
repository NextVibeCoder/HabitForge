package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.RegistroCumplimiento
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HabitoService {
    @GET("habitos")
    suspend fun getHabitos(): List<Habito>

    @POST("habitos")
    suspend fun crearHabito(@Body request: HabitoRequest): Habito

    @PUT("habitos/{id}")
    suspend fun editarHabito(
        @Path("id") id: Long,
        @Body request: HabitoRequest
    ): Habito

    @DELETE("habitos/{id}")
    suspend fun eliminarHabito(@Path("id") id: Long)

    @POST("habitos/{id}/completar")
    suspend fun completarHabito(
        @Path("id") id: Long,
        @Query("fecha") fecha: String
    ): RegistroCumplimiento

    @GET("habitos/{id}/calendario")
    suspend fun getCalendario(
        @Path("id") id: Long,
        @Query("anio") anio: Int,
        @Query("mes") mes: Int
    ): List<RegistroCumplimiento>
}