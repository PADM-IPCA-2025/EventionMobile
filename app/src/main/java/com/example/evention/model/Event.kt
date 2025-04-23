package com.example.evention.model

import java.util.Date

data class Event(
    val eventID: String,
    val userId: String,
    val name: String,
    val description: String,
    val startAt: Date,
    val endAt: Date,
    val price: Double,
    val createdAt: Date,
    val eventStatus: EventStatus,
    val eventstatus_id: String,
    val addressEvents: List<AddressEvent>,
    val eventPicture: String?
)

data class EventStatus(
    val eventStatusID: String,
    val status: String
)

data class AddressEvent(
    val addressEstablishmentID: String,
    val road: String,
    val roadNumber: Int,
    val postCode: String,
    val localtown: String
)