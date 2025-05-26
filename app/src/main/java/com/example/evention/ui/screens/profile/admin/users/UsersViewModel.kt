package com.example.evention.ui.screens.profile.admin.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.userRemoteDataSource

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        viewModelScope.launch {
            _users.value = remoteDataSource.getUsers()
        }
    }
}