package com.example.events.util.api

import com.example.events.util.model.Eventos
import retrofit2.Call
import retrofit2.http.GET

interface EventosApi {

    //Fetches lists of comic characters with optional filters.
    @GET("api/events")
    fun listarEventos(
    ): Call<List<Eventos>>
}