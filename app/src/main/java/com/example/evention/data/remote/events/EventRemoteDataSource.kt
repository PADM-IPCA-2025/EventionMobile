package com.example.evention.data.remote.events

import android.util.Log
import com.example.evention.model.Event
import com.example.evention.model.User
import okhttp3.MultipartBody
import java.io.File

class EventRemoteDataSource(private val api: EventApiService) {
    suspend fun getEvents(): List<Event> = api.getEvents()

    suspend fun getApprovedEvents(): List<Event> = api.getApprovedEvents()

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

}
