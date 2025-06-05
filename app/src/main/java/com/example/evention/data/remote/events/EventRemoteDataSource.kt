package com.example.evention.data.remote.events

import android.util.Log
import com.example.evention.model.AddressEventRequest
import com.example.evention.model.Event
import com.example.evention.model.EventRequest
import com.example.evention.model.EventResponse
import com.example.evention.model.RoutesEventRequest
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
    ): Event {
        val updateRequest = EventApiService.UpdateEventRequest(
            name = name,
            description = description,
            startAt = startAt,
            endAt = endAt,
            price = price
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
        road: String,
        roadNumber: Int,
        postCode: String,
        localtown: String,
        latitude: Double,
        longitude: Double,
        eventPicture: String? = null
    ): Response<EventResponse> {
        val addressRequest = AddressEventRequest(
            road = road,
            roadNumber = roadNumber,
            postCode = postCode,
            localtown = localtown,
            routes = listOf(
                RoutesEventRequest(
                    latitude = latitude,
                    longitude = longitude,
                    order = 0
                )
            )
        )

        val request = EventRequest(
            userId = userId,
            name = name,
            description = description,
            startAt = startAt, // <-- agora String
            endAt = endAt,     // <-- agora String
            price = price,
            eventStatusID = "11111111-1111-1111-1111-111111111111",
            addressEvents = listOf(addressRequest),
            eventPicture = eventPicture
        )

        return api.createEvent(request)
    }




}
