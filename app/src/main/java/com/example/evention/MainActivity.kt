package com.example.evention

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.evention.ui.navigation.AppNavHost
import com.example.evention.ui.screens.auth.login.LoginScreen
import com.example.evention.ui.screens.auth.register.RegisterScreen
import com.example.evention.ui.theme.EventionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventionTheme {
                val navController = rememberNavController()
                //LoginScreen(navController = navController)
                RegisterScreen(navController = navController)
//                AppNavHost()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {

    }
}