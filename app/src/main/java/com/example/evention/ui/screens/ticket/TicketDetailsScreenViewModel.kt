package com.example.evention.ui.screens.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import com.example.evention.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketDetailsScreenViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.ticketRemoteDataSource

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket: StateFlow<Ticket?> = _ticket

    fun loadTicketById(eventId: String) {
        viewModelScope.launch {
            try {
                val fetchedTicket = remoteDataSource.getTicketById(eventId)
                _ticket.value = fetchedTicket
            } catch (e: Exception) {
                _ticket.value = null
            }
        }
    }
}
