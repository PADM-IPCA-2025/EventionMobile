package com.example.evention.ui.screens.event.create

import UserPreferences
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
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

    private val _createSuccess = MutableStateFlow(false)
    val createSuccess: StateFlow<Boolean> = _createSuccess

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

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
        context: Context
    ) {
        viewModelScope.launch {
            _createEventState.value = CreateEventState.Loading

            val userId = userPreferences.getUserId()
            if (userId == null) {
                _createEventState.value = CreateEventState.Error("User authentication failed")
                return@launch
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNullOrEmpty()) {
                _createEventState.value = CreateEventState.Error("Address not found")
                return@launch
            }

            val addr = addresses[0]
            val road = addr.thoroughfare ?: ""
            val roadNumber = addr.subThoroughfare?.toIntOrNull() ?: 0
            val postCode = addr.postalCode ?: ""
            val locality = addr.locality ?: ""

            val defaultLocationId = "0281b20c-60d5-4805-bcc1-c17660feb55f"
            val localtown = try {
                locationRemoteDataSource.getLocationByLocaltown(locality).locationId
            } catch (e: Exception) {
                Log.e("EVENT_DEBUG", "Erro a obter localidade: ${e.message}")
                defaultLocationId
            }

            try {
                // Tratar imagem
                val imagePart = selectedImageUri.value?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)!!
                    val fileBytes = inputStream.readBytes()
                    inputStream.close()

                    val fileName = "event_${System.currentTimeMillis()}.jpg"
                    val requestFile = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("eventPicture", fileName, requestFile)
                }

                Log.d("EVENT_DEBUG", "Imagem criada: ${imagePart != null}")

                // Criar evento
                val response = eventRemoteDataSource.createEventWithImage(
                    userId = userId,
                    name = name,
                    description = description,
                    startAt = formatDateToIso(startAt),
                    endAt = formatDateToIso(endAt),
                    price = price,
                    eventPicture = imagePart
                )

                if (!response.isSuccessful || response.body() == null) {
                    Log.e("EVENT_DEBUG", "Erro a criar evento: ${response.code()} ${response.errorBody()?.string()}")
                    _createEventState.value = CreateEventState.Error("Erro a criar evento: ${response.code()}")
                    return@launch
                }

                val createdEvent = response.body()!!
                Log.d("EVENT_DEBUG", "Evento criado: ${createdEvent.eventID}")

                val eventId = createdEvent.eventID
                if (eventId.isNullOrEmpty()) {
                    _createEventState.value = CreateEventState.Error("ID do evento inválido.")
                    return@launch
                }

                // Criar endereço
                val addressResponse = eventRemoteDataSource.createAddressEvent(
                    AddressEventRequest(
                        event_id = eventId,
                        road = road,
                        roadNumber = roadNumber,
                        postCode = postCode,
                        localtown = localtown
                    )
                )

                if (!addressResponse.isSuccessful || addressResponse.body() == null) {
                    Log.e("EVENT_DEBUG", "Erro a criar endereço: ${addressResponse.code()} ${addressResponse.errorBody()?.string()}")
                    _createEventState.value = CreateEventState.Error("Erro a criar endereço")
                    return@launch
                }

                val addressId = addressResponse.body()!!.addressEstablishmentID
                Log.d("EVENT_DEBUG", "Endereço criado: $addressId")

                // Criar rota
                val routeResponse = eventRemoteDataSource.createRouteEvent(
                    RoutesEventRequest(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        order = 0,
                        addressEvent_id = addressId
                    )
                )

                if (!routeResponse.isSuccessful || routeResponse.body() == null) {
                    Log.e("EVENT_DEBUG", "Erro a criar rota: ${routeResponse.code()} ${routeResponse.errorBody()?.string()}")
                    _createEventState.value = CreateEventState.Error("Erro a criar rota")
                    return@launch
                }

                Log.d("EVENT_DEBUG", "Rota criada com sucesso")
                _createEventState.value = CreateEventState.Success(createdEvent)

            } catch (e: Exception) {
                Log.e("EVENT_DEBUG", "Erro geral: ${e.message}", e)
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