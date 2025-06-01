package com.example.evention.ui.screens.ticket

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.CreateFeedbackRequest
import com.example.evention.model.Feedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteDataSource = NetworkModule.ticketRemoteDataSource

    private val _createFeedbackResult = MutableStateFlow<Result<Feedback>?>(null)
    val createFeedbackResult: StateFlow<Result<Feedback>?> = _createFeedbackResult

    fun createFeedback(eventId: String, rating: Int, commentary: String) {
        viewModelScope.launch {
            try {
                val sharedPreferences = getApplication<Application>()
                    .getSharedPreferences("prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("TOKEN", null)

                if (token.isNullOrEmpty()) {
                    _createFeedbackResult.value = Result.failure(Exception("Token não encontrado"))
                    return@launch
                }

                val request = CreateFeedbackRequest(rating, commentary)
                val feedback = remoteDataSource.createFeedback(eventId, request)
                _createFeedbackResult.value = Result.success(feedback)
            } catch (e: Exception) {
                _createFeedbackResult.value = Result.failure(e)
            }
        }
    }

    fun clearFeedbackResult() {
        _createFeedbackResult.value = null
    }
}
