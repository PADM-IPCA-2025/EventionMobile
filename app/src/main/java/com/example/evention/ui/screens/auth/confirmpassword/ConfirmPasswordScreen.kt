package com.example.evention.ui.screens.auth.confirmpassword

import AuthConfirmButton
import AuthTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.R
import com.example.evention.ui.theme.EventionTheme
import kotlinx.coroutines.delay


@Composable
fun ConfirmPasswordScreen(
    navController: NavController,
    viewModel: ConfirmPasswordViewModel = viewModel()
) {
    var token by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    val confirmState by viewModel.state.collectAsState()

    val buttonState = when (confirmState) {
        is ConfirmPasswordViewModel.ConfirmPasswordState.Loading -> ButtonState.LOADING
        is ConfirmPasswordViewModel.ConfirmPasswordState.Success -> ButtonState.SUCCESS
        is ConfirmPasswordViewModel.ConfirmPasswordState.Error -> ButtonState.ERROR
        else -> ButtonState.IDLE
    }

    LaunchedEffect(confirmState) {
        when (confirmState) {
            is ConfirmPasswordViewModel.ConfirmPasswordState.Success -> {
                delay(500)
                viewModel.resetState()
                navController.navigate("signIn") {
                    popUpTo("confirmPassword") { inclusive = true }
                }
            }

            is ConfirmPasswordViewModel.ConfirmPasswordState.Error -> {
                delay(2000)
                viewModel.resetState()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Arrow Back",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
                .size(24.dp)
                .clickable { navController.navigate("resetPassword") },
            tint = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Confirm Password",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please enter the token sent by email and\nthe new password for your account",
            fontSize = 15.sp,
            color = Color(0xFF120D26),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        AuthTextField(
            placeholderText = "Your Token",
            iconResId = R.drawable.profile,
            value = token,
            password = false,
            onValueChange = { token = it }
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "Your Password",
            iconResId = R.drawable.lock,
            value = newPassword,
            password = true,
            onValueChange = { newPassword = it }
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "Confirm Password",
            iconResId = R.drawable.lock,
            value = confirmNewPassword,
            password = true,
            onValueChange = { confirmNewPassword = it }
        )

        Spacer(modifier = Modifier.height(50.dp))

        AuthConfirmButton(
            text = "Confirm",
            state = buttonState,
            onClick = {
                viewModel.resetState()
                viewModel.confirmPassword(token, newPassword, confirmNewPassword)
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        val navController = rememberNavController()
        ConfirmPasswordScreen(navController = navController)
    }
}