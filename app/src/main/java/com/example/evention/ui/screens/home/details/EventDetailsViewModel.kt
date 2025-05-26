package com.example.evention.ui.screens.home.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.eventRemoteDataSource

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    fun loadEventById(eventId: String) {
        viewModelScope.launch {
            try {
                val fetchedEvent = remoteDataSource.getEventById(eventId)
                _event.value = fetchedEvent
            } catch (e: Exception) {
                _event.value = null
            }
        }
    }
}
