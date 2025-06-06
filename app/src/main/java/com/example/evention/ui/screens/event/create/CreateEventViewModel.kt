package com.example.evention.ui.screens.event.create

import UserPreferences
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.data.remote.location.LocationRemoteDataSource
import com.example.evention.di.NetworkModule
import com.example.evention.di.NetworkModule.locationRemoteDataSource
import com.example.evention.model.AddressEventRequest
import com.example.evention.model.EventRequest
import com.example.evention.model.EventResponse
import com.example.evention.model.RoutesEventRequest
import com.google.android.gms.common.api.Response
import com.google.gson.Gson
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

    private val locationRemoteDataSource = NetworkModule.locationRemoteDataSource

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
                _createEventState.value = CreateEventState.Error("Utilizador não autenticado")
                return@launch
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNullOrEmpty()) {
                _createEventState.value = CreateEventState.Error("Não foi possível obter o endereço")
                return@launch
            }

            val addr = addresses[0]
            val road = addr.thoroughfare ?: ""
            val roadNumber = addr.subThoroughfare?.toIntOrNull() ?: 0
            val postCode = addr.postalCode ?: ""
            val locality = addr.locality ?: ""

            val gson = Gson()
            val defaultLocationId = "f0c19684-09c4-4960-bfe1-f8c2735d4f6d"
            val localtown = try {
                val locationResponse = locationRemoteDataSource.getLocationByLocaltown(locality)
                locationResponse.locationId
            } catch (e: Exception) {
                Log.w("EVENT_DEBUG", "⚠️ Erro ao buscar locationId, usando padrão: ${e.message}")
                defaultLocationId
            }

            Log.d("LOCALTOWN", "LOCALTOWN ID: $localtown")

            try {
                // 1. Criar evento primeiro
                val eventRequest = EventRequest(
                    userId = userId,
                    name = name,
                    description = description,
                    startAt = formatDateToIso(startAt),
                    endAt = formatDateToIso(endAt),
                    price = price,
                    eventStatusID = "11111111-1111-1111-1111-111111111111",
                    eventPicture = eventPicture
                )

                Log.d("EVENT_DEBUG", "📦 EventRequest JSON: ${gson.toJson(eventRequest)}")

                val eventResponse = eventRemoteDataSource.createEvent(
                    userId = userId,
                    name = name,
                    description = description,
                    startAt = formatDateToIso(startAt),
                    endAt = formatDateToIso(endAt),
                    price = price,
                    eventPicture = eventPicture
                )

                Log.d("EVENT_DEBUG", "✅ Event response: ${eventResponse.code()} ${eventResponse.message()}")

                if (!eventResponse.isSuccessful || eventResponse.body() == null) {
                    _createEventState.value = CreateEventState.Error("Erro ao criar evento: ${eventResponse.code()}")
                    return@launch
                }

                val createdEvent = eventResponse.body()!!

                Log.d("EVENT_DEBUG", "🔍 createdEventResponse JSON: ${gson.toJson(createdEvent)}")

                val eventId = createdEvent.eventID

                // 2. Criar endereço com event_id e localtown como ID
                val addressRequest = AddressEventRequest(
                    event_id = eventId,
                    road = road,
                    roadNumber = roadNumber,
                    postCode = postCode,
                    localtown = localtown
                )

                Log.d("EVENT_DEBUG", "📦 AddressEventRequest JSON: ${gson.toJson(addressRequest)}")

                val addressResponse = eventRemoteDataSource.createAddressEvent(addressRequest)

                Log.d("EVENT_DEBUG", "✅ Address response: ${addressResponse.code()} ${addressResponse.message()}")

                if (!addressResponse.isSuccessful || addressResponse.body() == null) {
                    _createEventState.value = CreateEventState.Error("Erro ao criar endereço")
                    return@launch
                }

                val createdAddress = addressResponse.body()!!
                val addressId = createdAddress.addressEstablishmentID

                // 3. Criar rota com addressEvent_id
                val routeRequest = RoutesEventRequest(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    order = 0,
                    addressEvent_id = addressId
                )

                Log.d("EVENT_DEBUG", "📦 RoutesEventRequest JSON: ${gson.toJson(routeRequest)}")

                val routeResponse = eventRemoteDataSource.createRouteEvent(routeRequest)

                Log.d("EVENT_DEBUG", "✅ Route response: ${routeResponse.code()} ${routeResponse.message()}")

                if (!routeResponse.isSuccessful || routeResponse.body() == null) {
                    _createEventState.value = CreateEventState.Error("Erro ao criar rota")
                    return@launch
                }

                _createEventState.value = CreateEventState.Success(createdEvent)

            } catch (e: Exception) {
                Log.e("EVENT_DEBUG", "❌ Exceção ao criar evento: ${e.message}", e)
                _createEventState.value = CreateEventState.Error("Erro de rede: ${e.message}")
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
