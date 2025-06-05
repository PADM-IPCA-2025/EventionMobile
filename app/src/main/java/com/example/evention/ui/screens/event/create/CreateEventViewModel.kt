package com.example.evention.ui.screens.event.create

import UserPreferences
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.AddressEventRequest
import com.example.evention.model.EventRequest
import com.example.evention.model.EventResponse
import com.example.evention.model.RoutesEventRequest
import com.google.android.gms.common.api.Response
import com.google.type.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CreateEventViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val eventRemoteDataSource = NetworkModule.eventRemoteDataSource

    private val _createEventState = MutableStateFlow<CreateEventState>(CreateEventState.Idle)
    val createEventState: StateFlow<CreateEventState> = _createEventState

    private fun formatDateToIso(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(date)
    }

    fun createEvent(
        name: String,
        description: String,
        startAt: Date,
        endAt: Date,
        price: Double,
        location: com.google.android.gms.maps.model.LatLng,
        context: Context,
        eventPicture: String? = null
    ) {
        viewModelScope.launch {
            _createEventState.value = CreateEventState.Loading

            val userId = userPreferences.getUserId()
            if (userId == null) {
                _createEventState.value = CreateEventState.Error("Usuário não autenticado")
                return@launch
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNullOrEmpty()) {
                _createEventState.value =
                    CreateEventState.Error("Não foi possível obter o endereço")
                return@launch
            }

            val addr = addresses[0]

            try {
                val response = eventRemoteDataSource.createEvent(
                    userId = userId,
                    name = name,
                    description = description,
                    startAt = formatDateToIso(startAt),  // 👈 formatado como ISO 8601
                    endAt = formatDateToIso(endAt),      // 👈 formatado como ISO 8601
                    price = price,
                    road = addr.thoroughfare ?: "",
                    roadNumber = addr.subThoroughfare?.toIntOrNull() ?: 0,
                    postCode = addr.postalCode ?: "",
                    localtown = addr.locality ?: "",
                    latitude = location.latitude,
                    longitude = location.longitude,
                    eventPicture = eventPicture
                )

                if (response.isSuccessful && response.body() != null) {
                    Log.d("CreateEvent", "Evento criado com sucesso: ${response.body()}")
                    _createEventState.value = CreateEventState.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CreateEvent", "Erro HTTP ${response.code()}: $errorBody")
                    _createEventState.value =
                        CreateEventState.Error("Erro: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                Log.e("CreateEvent", "Exceção ao criar evento", e)
                _createEventState.value =
                    CreateEventState.Error("Erro de rede: ${e.message}")
            }
        }
    }
}

sealed class CreateEventState {
    object Idle : CreateEventState()
    object Loading : CreateEventState()
    data class Success(val response: EventResponse) : CreateEventState()
    data class Error(val message: String) : CreateEventState()
}
