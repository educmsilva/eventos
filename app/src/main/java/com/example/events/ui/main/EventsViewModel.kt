package com.example.events.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.events.util.client.EventsWebClient
import com.example.events.util.model.CheckInRequest
import com.example.events.util.model.CheckInResponse
import com.example.events.util.model.Eventos
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.reflect.Type

class EventsViewModel : ViewModel() {

    private val eventosMutableLiveData = MutableLiveData<List<Eventos>?>()
    private var checkInRequest = MutableLiveData<CheckInRequest?>()
    private val errorMessageLiveData = MutableLiveData<String?>()
    private val checkInLiveData = MutableLiveData<CheckInResponse>()

    fun getEventosLive(): LiveData<List<Eventos>?> = eventosMutableLiveData
    fun getCheckInRequest(): LiveData<CheckInRequest?> = checkInRequest
    fun getErrorMessageLive(): LiveData<String?> = errorMessageLiveData
    fun getSucessMessageLive(): LiveData<CheckInResponse?> = checkInLiveData

    fun getEventos() {
        EventsWebClient().listarEventos({
            updateRequestEventos(it)
        }, {
            updateErrorMessage("Erro ao buscar os eventos. Erro: " + it)
        }
        )
    }

    fun checkIn(
        checkInRequest: CheckInRequest
    ) {
        EventsWebClient().checkIn({
            updateRequestCheckIn(it)
        }, {
            updateErrorMessage("Erro ao realizar check-in. Erro: " + it)
        },
            checkInRequest
        )
    }

    fun findEvent(eventID: String): Eventos? {
        val evento =
            getEventosLive().value?.find { it.id == eventID }
        return evento
    }

    fun updateCheckinUserdata(checkInRequest: CheckInRequest) {
        this.checkInRequest.postValue(checkInRequest)
    }

    private fun updateRequestEventos(response: Response<JsonElement>) {
        try {
            val gson = Gson()
            val toJson = gson.toJson(response.body())
            val collectionType: Type = object : TypeToken<List<Eventos?>?>() {}.type
            val events: List<Eventos> = gson.fromJson(toJson, collectionType)
            eventosMutableLiveData.postValue(events)
        } catch (e: Exception) {
            eventosMutableLiveData.postValue(null)
            updateErrorMessage("Erro ao buscar os eventos. Tente mais tarde.")
        }
    }

    private fun updateErrorMessage(errorMessage: String) {
        errorMessageLiveData.postValue(errorMessage)
    }

    private fun updateRequestCheckIn(response: Response<JsonElement>) {
        try {
            val gson = Gson()
            val toJson = gson.toJson(response.body())
            val checkInResponse: CheckInResponse =
                gson.fromJson(toJson, CheckInResponse::class.java)
            checkInLiveData.postValue(checkInResponse)
        } catch (e: java.lang.Exception) {
            checkInLiveData.postValue(null)
            updateErrorMessage("Erro ao realizar check-in. Tente mais tarde.")
        }
    }

}