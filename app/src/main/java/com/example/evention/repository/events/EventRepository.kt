package com.example.evention.repository.events

import com.example.evention.model.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
}
