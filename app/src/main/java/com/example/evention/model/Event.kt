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
