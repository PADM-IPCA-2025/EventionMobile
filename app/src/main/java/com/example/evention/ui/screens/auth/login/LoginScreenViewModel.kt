package com.example.evention.ui.screens.auth.login

import LoginRemoteDataSource
import LoginResponse
import UserPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log

class LoginScreenViewModel(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            val result = loginRemoteDataSource.login(email, password)
            loginState = if (result.isSuccess) {
                val response = result.getOrThrow()
                userPreferences.saveToken(response.token) // save token
                LoginState.Success(response)
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val response: LoginResponse) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
