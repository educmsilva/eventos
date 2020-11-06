package com.example.events.util.client

import com.example.events.util.RetrofitInitializer
import com.example.events.util.callback
import com.example.events.util.model.CheckInRequest
import com.google.gson.JsonElement
import retrofit2.Response


class EventsWebClient {

    fun listarEventos(
        success: (events: Response<JsonElement>) -> Unit,
        failure: (throwable: Throwable) -> Unit
    ) {
        val call = RetrofitInitializer().eventApiService().listarEventos()

        call.enqueue(
            callback(
                { response ->
                    response?.let {
                        success(it)
                    }
                },
                { throwable ->
                    throwable?.let {
                        failure(it)
                    }
                })
        )
    }

    fun checkIn(
        success: (Response<JsonElement>) -> Unit,
        failure: (throwable: Throwable) -> Unit,
        checkInRequest: CheckInRequest
    ) {
        val call =
            RetrofitInitializer().eventApiService().checkIn(checkInRequest)

        call.enqueue(
            callback(
                { response ->
                    response?.let {
                        success(it)
                    }
                },
                { throwable ->
                    throwable?.let {
                        failure(it)
                    }
                })
        )
    }

}