package com.example.evention.data.remote.tickets

import com.example.evention.model.Event
import com.example.evention.model.Ticket
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketApiService {
    @GET("userinevent/api/tickets")
    suspend fun getTickets(): List<Ticket>

    @GET("userinevent/api/tickets/{id}")
    suspend fun getTicketById(@Path("id") ticketId: String): Ticket
}