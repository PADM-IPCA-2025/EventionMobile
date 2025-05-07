package com.example.evention.data.remote.events

import com.example.evention.model.Event

class EventRemoteDataSource(private val api: EventApiService) {
    suspend fun getEvents(): List<Event> = api.getEvents()
}
