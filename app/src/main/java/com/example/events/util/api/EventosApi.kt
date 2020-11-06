package com.example.events.util.api

import com.example.events.util.model.CheckInRequest
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventosApi {

    //Fetches busca todos os eventos
    @GET("api/events")
    fun listarEventos(
    ): Call<JsonElement>

    //Realiza o checkin no evento
    @POST("api/checkin")
    fun checkIn(@Body checkInRequest: CheckInRequest): Call<JsonElement>
}