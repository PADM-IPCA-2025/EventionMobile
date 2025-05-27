package com.example.evention.ui.screens.auth.login

import LoginRemoteDataSource
import LoginResponse
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            val result = loginRemoteDataSource.login(email, password)
            loginState = if (result.isSuccess) {
                LoginState.Success(result.getOrThrow())
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Erro desconhecido")
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

