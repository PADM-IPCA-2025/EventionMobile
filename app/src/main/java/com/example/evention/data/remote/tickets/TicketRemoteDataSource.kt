package com.example.evention.data.remote.tickets

import com.example.evention.model.Event
import com.example.evention.model.Ticket

class TicketRemoteDataSource(private val api: TicketApiService) {
    suspend fun getTickets(): List<Ticket> = api.getTickets()

    suspend fun getTicketById(ticketId: String): Ticket = api.getTicketById(ticketId)
}
