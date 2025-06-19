package com.example.evention.ui.screens.auth.register

import AuthTextField
import AuthConfirmButton
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.R
import com.example.evention.data.remote.authentication.RegisterViewModelFactory
import com.example.evention.ui.components.auth.AuthGoogle
import com.example.evention.ui.theme.EventionTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.collectAsState
import com.example.evention.ui.screens.auth.login.signInWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: RegisterScreenViewModel = viewModel(factory = RegisterViewModelFactory(context))

    //GOOGLE AUTH
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.registerWithGoogle(idToken)
            } else {
                Log.e("GOOGLE", "Token inválido")
            }
        } catch (e: ApiException) {
            Log.e("GOOGLE", "Erro Google Sign-In", e)
        }
    }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsState()

    val buttonState = when (registerState) {
        is RegisterScreenViewModel.RegisterState.Loading -> ButtonState.LOADING
        is RegisterScreenViewModel.RegisterState.Success -> ButtonState.SUCCESS
        is RegisterScreenViewModel.RegisterState.Error -> ButtonState.ERROR
        else -> ButtonState.IDLE
    }

    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterScreenViewModel.RegisterState.Success -> {
                delay(2000)
                navController.navigate("signIn") {
                    popUpTo("signUp") { inclusive = true }
                }
            }

            is RegisterScreenViewModel.RegisterState.Error -> {
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
                .clickable { navController.navigate("signIn") },
            tint = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sign up",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "Full Name",
            iconResId = R.drawable.profile,
            value = username,
            password = false,
            onValueChange = { username = it }
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "abc@email.com",
            iconResId = R.drawable.mail,
            value = email,
            password = false,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "Your Password",
            iconResId = R.drawable.lock,
            value = password,
            password = true,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(
            placeholderText = "Confirm Password",
            iconResId = R.drawable.lock,
            value = confirmpassword,
            password = true,
            onValueChange = { confirmpassword = it }
        )

        Spacer(modifier = Modifier.height(50.dp))

        AuthConfirmButton(
            text = "Sign up",
            state = buttonState,
            onClick = {
                viewModel.resetState()
                viewModel.register(username, email, password, confirmpassword)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            "OR",
            color = Color(0xFF9D9898),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        AuthGoogle(
            text = "Registar com Google",
            onClick = {
                signInWithGoogle(context, launcher)
            }
        )


        Spacer(modifier = Modifier.height(120.dp))

        Row {
            Text(
                text = "Already have an account?",
                color = Color(0xFF120D26),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 15.sp
            )
            Text(
                text = " Sign in",
                modifier = Modifier.clickable { navController.navigate("signIn") },
                color = Color(0xFF5669FF),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 15.sp
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        val navController = rememberNavController()
        RegisterScreen(navController = navController)
    }
}