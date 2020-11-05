package com.example.events.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.events.util.client.EventsWebClient
import com.example.events.util.model.CheckInRequest
import com.example.events.util.model.CheckInResponse
import com.example.events.util.model.Eventos

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
            updateSucessMessage(it)
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

    fun clearMessages() {
        errorMessageLiveData.postValue("")
        checkInLiveData.postValue(null)
    }

    private fun updateRequestEventos(eventos: List<Eventos>) {
        eventosMutableLiveData.postValue(eventos)
    }

    private fun updateErrorMessage(errorMessage: String) {
        errorMessageLiveData.postValue(errorMessage)
    }

    private fun updateSucessMessage(checkInResponse: CheckInResponse) {
        checkInLiveData.postValue(checkInResponse)
    }

}