package com.example.evention.ui.screens.event.create

import UserPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.AddressEventRequest
import com.example.evention.model.EventRequest
import com.example.evention.model.EventResponse
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CreateEventViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

//    private val eventRemoteDataSource = NetworkModule.eventRemoteDataSource
//
//    private val _createEventState = MutableStateFlow<CreateEventState>(CreateEventState.Idle)
//    val createEventState: StateFlow<CreateEventState> = _createEventState
//
//    fun createEvent(
//        name: String,
//        description: String,
//        startAt: Date,
//        endAt: Date,
//        price: Double,
//        address: String,
//        eventPicture: String? = null
//    ) {
//        viewModelScope.launch {
//            _createEventState.value = CreateEventState.Loading
//
//            val userId = userPreferences.getUserId()
//            if (userId == null) {
//                _createEventState.value = CreateEventState.Error("Usuário não autenticado")
//                return@launch
//            }
//
//            val addressRequest = listOf(
//                AddressEventRequest(address)
//            )
//
//            val request = EventRequest(
//                userId = userId,
//                name = name,
//                description = description,
//                startAt = startAt,
//                endAt = endAt,
//                price = price,
//                eventStatusID = "1", // ou o status padrão que sua API exige
//                addressEvents = addressRequest,
//                eventPicture = eventPicture
//            )
//
//            try {
//                val response = NetworkModule.eventRemoteDataSource.api.createEvent(request)
//
//                if (response.isSuccessful && response.body() != null) {
//                    _createEventState.value = CreateEventState.Success(response.body()!!)
//                } else {
//                    _createEventState.value = CreateEventState.Error("Erro: ${response.code()}")
//                }
//
//            } catch (e: Exception) {
//                _createEventState.value = CreateEventState.Error("Erro de rede: ${e.message}")
//            }
//        }
//    }
}


sealed class CreateEventState {
    object Idle : CreateEventState()
    object Loading : CreateEventState()
    data class Success(val response: EventResponse) : CreateEventState()
    data class Error(val message: String) : CreateEventState()
}
