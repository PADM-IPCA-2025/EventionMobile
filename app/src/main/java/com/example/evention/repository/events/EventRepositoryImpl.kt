package com.example.evention.repository.events

import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.model.Event

class EventRepositoryImpl(
    private val remote: EventRemoteDataSource
) : EventRepository {

    override suspend fun getEvents(): List<Event> {
        return remote.getEvents()
    }
}

