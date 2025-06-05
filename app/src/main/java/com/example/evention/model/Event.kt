package com.example.evention.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    val localtown: String,
    val routes: List<RoutesEvent>
)
data class RoutesEvent(
    val routeID: String,
    val latitude: Double,
    val longitude: Double,
    val order: Int
)

data class EventRequest(
    val userId: String,
    val name: String,
    val description: String,
    val startAt: String,
    val endAt: String,
    val price: Double,
    val eventStatusID: String,
    val addressEvents: List<AddressEventRequest>,
    val eventPicture: String? = null
)

data class AddressEventRequest(
    val road: String,
    val roadNumber: Int,
    val postCode: String,
    val localtown: String,
    val routes: List<RoutesEventRequest>
)

data class RoutesEventRequest(
    val latitude: Double,
    val longitude: Double,
    val order: Int
)

data class EventResponse(
    val event: Event
)
