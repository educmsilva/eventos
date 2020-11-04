package com.example.events.util

import com.example.events.util.api.EventosApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    companion object {
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://5f5a8f24d44d640016169133.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    fun eventApiService() = getRetrofitInstance().create(EventosApi::class.java)
}