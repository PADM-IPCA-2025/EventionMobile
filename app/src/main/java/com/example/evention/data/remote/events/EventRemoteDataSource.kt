package com.example.evention.data.remote.events

import android.util.Log
import com.example.evention.model.AddressEventRequest
import com.example.evention.model.AddressEventResponse
import com.example.evention.model.Event
import com.example.evention.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import com.example.evention.model.EventRequest
import com.example.evention.model.EventResponse
import com.example.evention.model.RoutesEventRequest
import com.example.evention.model.RoutesEventResponse
import retrofit2.Response
import java.util.Date

class EventRemoteDataSource(private val api: EventApiService) {
    suspend fun getEvents(): List<Event> = api.getEvents()

    suspend fun getApprovedEvents(): List<Event> = api.getApprovedEvents()

    suspend fun getUserReputation(userId: String) = api.getUserReputation(userId)

    suspend fun getMyEvents(): List<Event> = api.getMyEvents()

    suspend fun getEventById(eventId: String): Event = api.getEventById(eventId)

    suspend fun getSuspendedEvents(): List<Event> = api.getSuspendedEvents()

    suspend fun deleteEvent(eventId: String) = api.deleteEvent(eventId)

    suspend fun approveEvent(eventId: String) = api.approveEvent(eventId)

    suspend fun updateEvent(
        eventId: String,
        name: String,
        description: String,
        startAt: String,
        endAt: String,
        price: Float,
        eventPicture: MultipartBody.Part? = null
    ): Event {
        val namePart = name.toRequestBody("text/plain".toMediaType())
        val descriptionPart = description.toRequestBody("text/plain".toMediaType())
        val startAtPart = startAt.toRequestBody("text/plain".toMediaType())
        val endAtPart = endAt.toRequestBody("text/plain".toMediaType())
        val pricePart = price.toString().toRequestBody("text/plain".toMediaType())

        return api.updateEvent(
            eventId = eventId,
            name = namePart,
            description = descriptionPart,
            startAt = startAtPart,
            endAt = endAtPart,
            price = pricePart,
            eventPicture = eventPicture
        )
        Log.d("body eventId", eventId)
        Log.d("body name", updateRequest.name)
        Log.d("body description", updateRequest.description)
        Log.d("body startAt", updateRequest.startAt)
        Log.d("body endAt", updateRequest.endAt)
        Log.d("body price", updateRequest.price.toString())
        return api.updateEvent(eventId, updateRequest)
    }

    suspend fun createEvent(
        userId: String,
        name: String,
        description: String,
        startAt: String, // <-- alterado para String
        endAt: String,   // <-- alterado para String
        price: Double,
        eventPicture: String? = null
    ): Response<EventResponse> {

        val request = EventRequest(
            userId = userId,
            name = name,
            description = description,
            startAt = startAt,
            endAt = endAt,
            price = price,
            eventStatusID = "11111111-1111-1111-1111-111111111111",
            eventPicture = eventPicture
        )

        return api.createEvent(request)
    }

    suspend fun createAddressEvent(request: AddressEventRequest): Response<AddressEventResponse> {
        return api.createAddressEvent(request)
    }

    suspend fun createRouteEvent(request: RoutesEventRequest): Response<RoutesEventResponse> {
        return api.createRouteEvent(request)
    }

}
