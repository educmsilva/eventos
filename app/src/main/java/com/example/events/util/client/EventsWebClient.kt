package com.example.events.util.client

import com.example.events.util.RetrofitInitializer
import com.example.events.util.callback
import com.example.events.util.model.Eventos

class EventsWebClient {

    fun listarEventos(
        success: (events: List<Eventos>) -> Unit,
        failure: (throwable: Throwable) -> Unit
    ) {
        val call = RetrofitInitializer().eventApiService().listarEventos()

        call.enqueue(
            callback({ response ->
                response?.body()?.let {
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