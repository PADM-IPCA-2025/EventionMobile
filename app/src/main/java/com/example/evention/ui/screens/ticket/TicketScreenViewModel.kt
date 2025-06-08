package com.example.evention.ui.screens.ticket

import TicketRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.data.local.entities.EventEntity
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.di.NetworkModule
import com.example.evention.model.AddressEvent
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TicketScreenViewModel(
    private val repository: TicketRepository
) : ViewModel() {

    private val _createTicketResult = MutableStateFlow<Result<Unit>?>(null)
    val createTicketResult: StateFlow<Result<Unit>?> = _createTicketResult

    private val _ticketId = MutableStateFlow<String?>(null)
    val ticketId: StateFlow<String?> = _ticketId

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets

    private val remoteDataSource = NetworkModule.eventRemoteDataSource
    private val ticketRemoteDataSource = NetworkModule.ticketRemoteDataSource

    init {
        viewModelScope.launch {
            try {
                repository.syncTickets()
            } catch (e: Exception) {
                Log.w("TicketScreenVM", "Sync failed, using local data", e)
            }

            repository.getLocalTickets().collect { localTickets ->
                val ticketsWithEvents = localTickets.map { entity ->
                    val event = try {
                        remoteDataSource.getEventById(entity.event_id)
                    } catch (e: Exception) {
                        repository.getLocalEventById(entity.event_id)?.toDomain()
                    }

                    Ticket(
                        ticketID = entity.ticketID,
                        event = event!!
                    )
                }
                _tickets.value = ticketsWithEvents
            }
        }
    }

    fun createTicket(eventId: String) {
        viewModelScope.launch {
            try {
                val ticket = ticketRemoteDataSource.createTicket(eventId)
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


