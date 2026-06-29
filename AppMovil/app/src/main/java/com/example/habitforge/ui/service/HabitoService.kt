package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.model.dto.HabitoRequest
import com.example.habitforge.ui.model.RegistroCumplimiento
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HabitoService {
    @POST("habitos")
    suspend fun crearHabito(@Body request: HabitoRequest): Habito

    @PUT("habitos/{id}")
    suspend fun editarHabito(@Path("id") id: Long, @Body request: HabitoRequest): Habito

    @DELETE("habitos/{id}")
    suspend fun eliminarHabito(@Path("id") id: Long)

    @GET("habitos/{id}")
    suspend fun getHabitoById(@Path("id") id: Long): Habito

    @GET("habitos/individuales")
    suspend fun getHabitosIndividuales(): List<Habito>

    @GET("habitos/compartidos")
    suspend fun getHabitosCompartidos(): List<Habito>

    @GET("habitos/invitaciones")
    suspend fun getInvitacionesPendientes(): List<Habito>

    @POST("habitos/{id}/aceptar")
    suspend fun aceptarInvitacion(@Path("id") id: Long)

    @POST("habitos/{id}/rechazar")
    suspend fun rechazarInvitacion(@Path("id") id: Long)

    @POST("habitos/{id}/invitar")
    suspend fun invitarAmigos(@Path("id") id: Long, @Body emails: List<String>): Habito
}
