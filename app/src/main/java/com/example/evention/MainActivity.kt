package com.example.evention

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.evention.ui.screens.home.payment.PaymentMethod
import com.example.evention.ui.screens.home.payment.PaymentScreen
import com.example.evention.ui.theme.EventionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventionTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        PaymentScreen(listOf(
            PaymentMethod("Paypal", true),
            PaymentMethod("Credit Card", false)
        ))
    }
}