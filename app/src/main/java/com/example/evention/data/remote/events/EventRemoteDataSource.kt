package com.example.evention.data.remote.events

import com.example.evention.model.Event

class EventRemoteDataSource(private val api: EventApiService) {
    suspend fun getEvents(): List<Event> = api.getEvents()

    suspend fun getEventById(eventId: String): Event = api.getEventById(eventId)

    suspend fun getSuspendedEvents(): List<Event> = api.getSuspendedEvents()

    suspend fun deleteEvent(eventId: String) = api.deleteEvent(eventId)
}
