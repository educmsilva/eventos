package com.example.events.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.events.util.client.EventsWebClient
import com.example.events.util.model.Eventos

class EventosViewModel : ViewModel() {

    private val eventosMutableLiveData =
        MutableLiveData<List<Eventos>?>().apply { value = null }
    private val errorMessageLiveData =
        MutableLiveData<String?>().apply { value = "" }

    fun getEventosLive(): LiveData<List<Eventos>?> = eventosMutableLiveData
    fun getErrorMessageLive(): LiveData<String?> = errorMessageLiveData

    fun getEventos() {
        EventsWebClient().listarEventos({
            updateRequest(it)
        }, {
            updateErrorMessage("Erro ao buscar os eventos. Erro: " + it)
        }
        )
    }

    private fun updateRequest(eventos: List<Eventos>) {
        eventosMutableLiveData.postValue(eventos)
    }

    private fun updateErrorMessage(errorMessage: String) {
        errorMessageLiveData.postValue(errorMessage)
    }

}