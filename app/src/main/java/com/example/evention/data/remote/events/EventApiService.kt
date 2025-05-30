package com.example.evention.data.remote.events

import com.example.evention.model.Event
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventApiService {
    @GET("event/api/events")
    suspend fun getEvents(): List<Event>

    @GET("event/api/events/my")
    suspend fun getMyEvents(): List<Event>

    @GET("event/api/event/{id}")
    suspend fun getEventById(@Path("id") eventId: String): Event

    @GET("event/api/events/suspended")
    suspend fun getSuspendedEvents(): List<Event>

    @PUT("event/api/events/{id}/status")
    suspend fun approveEvent(@Path("id") eventId: String): Event

    @DELETE("event/api/events/{id}")
    suspend fun deleteEvent(@Path("id") eventId: String)

    data class UpdateEventRequest(
        val name: String,
        val description: String,
        val startAt: Long,
        val endAt: Long,
        val price: Float
    )

    @PUT("event/api/events/{id}")
    suspend fun updateEvent(
        @Path("id") eventId: String,
        @Body eventData: UpdateEventRequest
    ): Event


}