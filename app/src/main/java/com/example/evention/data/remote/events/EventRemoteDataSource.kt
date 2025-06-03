package com.example.evention.data.remote.events

import android.util.Log
import com.example.evention.model.Event
import com.example.evention.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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
    }
}
