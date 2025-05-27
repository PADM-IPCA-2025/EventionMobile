package com.example.evention.model

import com.google.gson.annotations.SerializedName


data class Ticket(
    val ticketID: String,
    val event: Event,
)

data class CreateTicketRequest(
    @SerializedName("event_id") val eventId: String
)