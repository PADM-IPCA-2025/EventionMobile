package com.example.evention.ui.screens.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import com.example.evention.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketScreenViewModel : ViewModel() {
    private val remoteDataSource = NetworkModule.ticketRemoteDataSource

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets

    private val _createTicketResult = MutableStateFlow<Result<Ticket>?>(null)
    val createTicketResult: StateFlow<Result<Ticket>?> = _createTicketResult

    private val _ticketId = MutableStateFlow<String?>(null)
    val ticketId: StateFlow<String?> = _ticketId

    init {
        viewModelScope.launch {
            val rawTickets = remoteDataSource.getTickets()
            val eventRemote = NetworkModule.eventRemoteDataSource

            val enrichedTickets = rawTickets.map { rawTicket ->
                val event = eventRemote.getEventById(rawTicket.event_id)
                Ticket(
                    ticketID = rawTicket.ticketID,
                    event = event
                )
            }

            _tickets.value = enrichedTickets
        }
    }

    fun createTicket(eventId: String) {
        viewModelScope.launch {
            try {
                val ticket = remoteDataSource.createTicket(eventId)
                _createTicketResult.value = Result.success(ticket)
                _ticketId.value = ticket.ticketID
            } catch (e: Exception) {
                _createTicketResult.value = Result.failure(e)
            }
        }
    }
    fun clearCreateResult() {
        _createTicketResult.value = null
    }


}

