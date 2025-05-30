package com.example.evention.data.remote.authentication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evention.di.NetworkModule
import com.example.evention.ui.screens.auth.register.RegisterScreenViewModel

class RegisterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val registerRemoteDataSource = NetworkModule.registerRemoteDataSource
        return RegisterScreenViewModel(registerRemoteDataSource) as T
    }
}
