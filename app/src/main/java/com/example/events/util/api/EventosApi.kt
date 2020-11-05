package com.example.events.util.api

import com.example.events.util.model.CheckInRequest
import com.example.events.util.model.CheckInResponse
import com.example.events.util.model.Eventos
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventosApi {

    //Fetches busca todos os eventos
    @GET("api/events")
    fun listarEventos(
    ): Call<List<Eventos>>

    //Realiza o checkin no evento
    @POST("api/checkin")
    fun checkIn(@Body checkInRequest: CheckInRequest): Call<CheckInResponse>
}