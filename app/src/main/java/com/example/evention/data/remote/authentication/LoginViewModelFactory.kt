package com.example.evention.data.remote.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evention.di.NetworkModule
import com.example.evention.ui.screens.auth.login.LoginScreenViewModel

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userRemoteDataSource = NetworkModule.loginRemoteDataSource
        return LoginScreenViewModel(userRemoteDataSource) as T
    }
}