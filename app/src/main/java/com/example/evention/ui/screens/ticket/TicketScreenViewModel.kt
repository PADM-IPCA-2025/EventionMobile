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

    init {
        viewModelScope.launch {
            _tickets.value = remoteDataSource.getTickets()
        }
    }

    fun createTicket(eventId: String, token: String) {
        viewModelScope.launch {
            try {
                val ticket = remoteDataSource.createTicket(eventId, token)
                _createTicketResult.value = Result.success(ticket)
            } catch (e: Exception) {
                _createTicketResult.value = Result.failure(e)
            }
        }
    }
    fun clearCreateResult() {
        _createTicketResult.value = null
    }


}

