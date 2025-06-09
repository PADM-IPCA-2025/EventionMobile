package com.example.evention.ui.screens.ticket

import TicketRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.data.local.entities.EventEntity
import com.example.evention.di.NetworkModule
import com.example.evention.model.AddressEvent
import com.example.evention.model.Event
import com.example.evention.model.EventStatus
import com.example.evention.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

class TicketDetailsScreenViewModel(
    private val repository: TicketRepository
) : ViewModel() {

    private val ticketRemote = NetworkModule.ticketRemoteDataSource
    private val eventRemote = NetworkModule.eventRemoteDataSource

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket: StateFlow<Ticket?> = _ticket

    fun loadTicketById(ticketId: String) {
        viewModelScope.launch {
            try {
                val remoteTicket = ticketRemote.getTicketById(ticketId)
                val remoteEvent = eventRemote.getEventById(remoteTicket.event_id)

                _ticket.value = Ticket(
                    ticketID = remoteTicket.ticketID,
                    user_id = remoteTicket.user_id,
                    feedback_id = remoteTicket.feedback_id,
                    participated = remoteTicket.participated,
                    event = remoteEvent
                )

                repository.syncTickets()

            } catch (e: Exception) {
                Log.w("TicketDetailsVM", "Erro remoto, tentando local", e)

                val localTicket = repository.getTicketById(ticketId)
                val localEvent = localTicket?.let { repository.getLocalEventById(it.event_id) }

                if (localTicket != null && localEvent != null) {
                    _ticket.value = Ticket(
                        ticketID = localTicket.ticketID,
                        user_id = localTicket.user_id,
                        feedback_id = localTicket.feedback_id,
                        participated = localTicket.participated,
                        event = localEvent.toDomain()
                    )
                } else {
                    _ticket.value = null
                }
            }
        }
    }

    fun EventEntity.toDomain(): Event {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

        val startDate = dateFormat.parse(this.startAt)

        val endDate = dateFormat.parse(this.endAt)

        return Event(
            eventID = this.eventID,
            userId = this.userId,
            name = this.name,
            description = this.description,
            startAt = startDate!!,
            endAt = endDate!!,
            price = this.price,
            eventPicture = this.eventPicture,
            createdAt = Date(),
            eventStatus = EventStatus(
                eventStatusID = "",
                status = ""
            ),
            addressEvents = listOf<AddressEvent>()
        )
    }

}
