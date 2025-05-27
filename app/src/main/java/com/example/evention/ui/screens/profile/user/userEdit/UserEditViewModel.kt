package com.example.evention.ui.screens.profile.user.userEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.di.NetworkModule
import com.example.evention.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserEditViewModel : ViewModel() {

    private val remoteDataSource = NetworkModule.userRemoteDataSource

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadUserById(userId: String) {
        viewModelScope.launch {
            try {
                val fetchedUser = remoteDataSource.getUserById(userId)
                _user.value = fetchedUser
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    fun editUser(userId: String, username: String, email: String, phone: Int) {
        viewModelScope.launch {
            try {
                remoteDataSource.updateUser(
                    userId = userId,
                    username = username,
                    email = email,
                    phone = phone
                )
                _user.value = _user.value?.copy(username = username, email = email, phone = phone)
            } catch (e: Exception) {
                // TODO: handle error (ex: mostrar toast/snackbar)
            }
        }
    }

}