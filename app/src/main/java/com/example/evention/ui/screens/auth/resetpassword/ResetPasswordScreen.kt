package com.example.evention.ui.screens.auth.resetpassword

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.R
import com.example.evention.ui.theme.EventionTheme

@Composable
fun ResetPasswordScreen(navController: NavController) {

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
                .clickable { navController.navigate("signIn") },
            tint = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Reset Password",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please enter your email address to\nrequest a password reset",
            fontSize = 15.sp,
            color = Color(0xFF120D26),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        var email by remember { mutableStateOf("") }

        AuthTextField(
            placeholderText = "abc@email.com",
            iconResId = R.drawable.mail,
            value = email,
            password = false,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthConfirmButton("SEND", onClick = { /* Lógica do reset password */ })

    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        val navController = rememberNavController()
        ResetPasswordScreen(navController = navController)
    }
}