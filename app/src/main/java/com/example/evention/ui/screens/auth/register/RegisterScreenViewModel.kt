package com.example.evention.ui.screens.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.evention.data.remote.authentication.RegisterRemoteDataSource
import com.example.evention.data.remote.authentication.RegisterRequest
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val registerRemoteDataSource: RegisterRemoteDataSource
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(username: String, email: String, password: String, confirmPassword: String) {

        Log.d("RegisterVM", "register() called with $username, $email")

        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _registerState.value = RegisterState.Error("All fields are required")
            return
        }

        if (password != confirmPassword) {
            _registerState.value = RegisterState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {

            Log.d("RegisterVM", "Coroutine started")

            _registerState.value = RegisterState.Loading
            try {
                val result = registerRemoteDataSource.register(username, email, password)
                result
                    .onSuccess { response ->
                        if (response.success) {
                            _registerState.value = RegisterState.Success
                        } else {
                            _registerState.value = RegisterState.Error(response.message)
                        }
                    }
                    .onFailure { e ->
                        _registerState.value = RegisterState.Error("Erro: ${e.message}")
                    }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Erro inesperado: ${e.message}")
            }
        }
    }

    fun resetState() {
        _registerState.value = RegisterState.Idle
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}

