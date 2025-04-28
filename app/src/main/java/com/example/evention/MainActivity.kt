package com.example.evention

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.evention.model.AddressEvent
import com.example.evention.model.Event
import com.example.evention.model.EventStatus
import com.example.evention.ui.screens.home.HomeScreen
import com.example.evention.ui.screens.home.payment.PaymentMethod
import com.example.evention.ui.screens.home.payment.PaymentScreen
import com.example.evention.ui.theme.EventionTheme
import java.util.Date

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

    }
}