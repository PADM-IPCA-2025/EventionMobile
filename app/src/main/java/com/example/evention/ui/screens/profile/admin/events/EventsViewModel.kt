package com.example.evention.ui.screens.profile.admin.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.eventRemoteDataSource

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        viewModelScope.launch {
            try {
                _events.value = remoteDataSource.getEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            try {
                remoteDataSource.deleteEvent(eventId)
                _events.value = _events.value.filter { it.eventID != eventId }
            } catch (e: Exception) {
                // TODO: handle error (ex: show error message)
            }
        }
    }
}