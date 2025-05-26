package com.example.evention.ui.screens.auth.login

import UserPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableLiveData<Result<String>>()
    val loginState: LiveData<Result<String>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val body = response.body() ?: return@launch
                    val token = body.token
                    userPreferences.saveToken(token)

                    val fcmToken = FirebaseMessaging.getInstance().token.await()

                    Firebase.firestore.collection("user_tokens")
                        .document(userGuid)
                        .set(
                            mapOf(
                                "fcmToken" to fcmToken,
                                "updatedAt" to FieldValue.serverTimestamp()
                            )
                        ).await()

                    _loginState.value = Result.success("Login e token guardado.")
                } else {
                    _loginState.value = Result.failure(Exception("Login falhou"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }
}
