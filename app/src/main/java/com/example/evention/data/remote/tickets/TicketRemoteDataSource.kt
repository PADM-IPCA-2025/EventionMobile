package com.example.evention.data.remote.tickets

import com.example.evention.model.CreateTicketRequest
import com.example.evention.model.Event
import com.example.evention.model.Ticket

class TicketRemoteDataSource(private val api: TicketApiService) {
    suspend fun getTickets(): List<Ticket> = api.getTickets()

    suspend fun getTicketById(ticketId: String): Ticket = api.getTicketById(ticketId)

    suspend fun createTicket(eventId: String, token: String): Ticket {
        val request = CreateTicketRequest(eventId)
        return api.createTicket(request, "Bearer $token")
    }
}
