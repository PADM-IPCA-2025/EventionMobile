package com.example.evention.ui.screens.profile.admin.editEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.Event
import com.example.evention.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class EditEventViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.eventRemoteDataSource

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _editSuccess = MutableStateFlow(false)
    val editSuccess: StateFlow<Boolean> = _editSuccess

    fun editEvent(
        eventId: String,
        name: String,
        description: String,
        startAt: String,
        endAt: String,
        price: Float,
    ) {
        viewModelScope.launch {
            try {
                val updated = remoteDataSource.updateEvent(
                    eventId = eventId,
                    name = name,
                    description = description,
                    startAt = startAt,
                    endAt = endAt,
                    price = price,
                )
                _event.value = updated
                _editSuccess.value = true
            } catch (e: Exception) {
                // TODO: handle error
                _editSuccess.value = false
            }
        }
    }

    fun clearEditSuccess() {
        _editSuccess.value = false
    }
}
