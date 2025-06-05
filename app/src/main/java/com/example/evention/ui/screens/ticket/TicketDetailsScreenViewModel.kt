package com.example.evention.ui.screens.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketDetailsScreenViewModel : ViewModel() {

    private val ticketRemote = NetworkModule.ticketRemoteDataSource
    private val eventRemote = NetworkModule.eventRemoteDataSource

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket: StateFlow<Ticket?> = _ticket

    fun loadTicketById(ticketId: String) {
        viewModelScope.launch {
            try {
                val rawTicket = ticketRemote.getTicketById(ticketId)
                val event = eventRemote.getEventById(rawTicket.event_id)

                val enrichedTicket = Ticket(
                    ticketID = rawTicket.ticketID,
                    user_id = rawTicket.user_id,
                    feedback_id = rawTicket.feedback_id,
                    participated = rawTicket.participated,
                    event = event
                )

                _ticket.value = enrichedTicket
            } catch (e: Exception) {
                _ticket.value = null
            }
        }
    }
}
