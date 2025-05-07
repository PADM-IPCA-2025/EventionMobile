package com.example.evention.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.evention.data.remote.events.EventRemoteDataSource

class HomeScreenViewModel(
    private val remoteDataSource: EventRemoteDataSource
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        viewModelScope.launch {
            _events.value = remoteDataSource.getEvents()
        }
    }
}
