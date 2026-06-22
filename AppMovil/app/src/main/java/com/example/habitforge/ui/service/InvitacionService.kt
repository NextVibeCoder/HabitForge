package com.example.habitforge.ui.service

import com.example.habitforge.ui.model.HabitoParticipante
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InvitacionService {

    @POST("habitos/{id}/invitaciones")
    suspend fun invitar(
        @Path("id") habitoId: Long,
        @Query("usuario") usernameOEmail: String
    ): HabitoParticipante

    @POST("invitaciones/{id}/aceptar")
    suspend fun aceptar(@Path("id") participanteId: Long): HabitoParticipante

    @POST("invitaciones/{id}/rechazar")
    suspend fun rechazar(@Path("id") participanteId: Long)

    @GET("invitaciones/pendientes")
    suspend fun getPendientes(): List<HabitoParticipante>

    @GET("habitos/{id}/participantes")
    suspend fun getParticipantes(@Path("id") habitoId: Long): List<HabitoParticipante>
}