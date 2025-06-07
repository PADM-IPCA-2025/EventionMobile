package com.example.evention.ui.screens.ticket

import TicketRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import com.example.evention.model.EventStatus
import com.example.evention.model.Ticket
import com.example.evention.model.TicketRaw
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

class TicketScreenViewModel(
    private val repository: TicketRepository,
    private val eventRemoteDataSource: EventRemoteDataSource = NetworkModule.eventRemoteDataSource
) : ViewModel() {

    private val _createTicketResult = MutableStateFlow<Result<Unit>?>(null)
    val createTicketResult: StateFlow<Result<Unit>?> = _createTicketResult

    private val _ticketId = MutableStateFlow<String?>(null)
    val ticketId: StateFlow<String?> = _ticketId

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets

    init {
        viewModelScope.launch {
            try {
                repository.syncTickets()
                repository.getLocalTickets().collect { localTickets ->
                    val ticketsWithEvents = localTickets.map { entity ->
                        Ticket(
                            ticketID = entity.ticketID,
                            event = eventRemoteDataSource.getEventById(entity.event_id)
                        )
                    }
                    _tickets.value = ticketsWithEvents
                }
            } catch (e: Exception) {
                Log.e("TicketScreenVM", "Erro ao sincronizar tickets", e)
            }
        }
    }

    fun createTicket(eventId: String) {
        viewModelScope.launch {
            try {
                val ticket = repository.createTicket(eventId) as TicketRaw
                _createTicketResult.value = Result.success(Unit)
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



