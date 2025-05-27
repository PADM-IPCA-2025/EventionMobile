package com.example.evention.data.remote.events

import com.example.evention.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {
    @GET("event/api/events")
    suspend fun getEvents(): List<Event>

    @GET("event/api/event/{id}")
    suspend fun getEventById(@Path("id") eventId: String): Event

    @GET("event/api/suspended")
    suspend fun getSuspendedEvents(): List<Event>
}