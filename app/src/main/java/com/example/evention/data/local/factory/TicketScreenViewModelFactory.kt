package com.example.evention.data.local.factory

import TicketRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evention.ui.screens.ticket.TicketScreenViewModel

class TicketScreenViewModelFactory(
    private val repository: TicketRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
