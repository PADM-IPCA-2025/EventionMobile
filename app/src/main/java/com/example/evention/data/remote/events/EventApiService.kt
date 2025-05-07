package com.example.evention.data.remote.events

import com.example.evention.model.Event
import retrofit2.http.GET

interface EventApiService {
    @GET("event/api/events")
    suspend fun getEvents(): List<Event>
}