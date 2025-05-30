package com.example.evention.data.remote.authentication

import UserPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun RequireAuth(
    navController: NavController,
    userPreferences: UserPreferences,
    content: @Composable () -> Unit
) {
    val isLoggedIn = remember {
        userPreferences.getToken() != null && userPreferences.getUserId() != null
    }

    LaunchedEffect(Unit) {
        if (!isLoggedIn) {
            delay(100)
            navController.navigate("signIn") {
                popUpTo("home") { inclusive = true } //opcional
            }
        }
    }

    if (isLoggedIn) {
        content()
    }
}