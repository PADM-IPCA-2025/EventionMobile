package com.example.evention.ui.screens.profile.user.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.userRemoteDataSource

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val fetchedUser = remoteDataSource.getUserProfile()
                _user.value = fetchedUser
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }
}