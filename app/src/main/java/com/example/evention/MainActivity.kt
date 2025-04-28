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
                val addressEvent = listOf(
                    AddressEvent(
                        "123",
                        "Rua Dr. Augusto Monteiro",
                        123,
                        "4750",
                        "Barcelos"
                    ),
                    AddressEvent(
                        "456",
                        "Rua Do Queimado",
                        123,
                        "4750",
                        "Barcelos"
                    ),
                    AddressEvent(
                        "789",
                        "Rua Barbosa Almeida",
                        123,
                        "4750",
                        "Barcelos"
                    ),
                )

                val eventStatus = listOf(
                    EventStatus(
                        "123",
                        "Pendente"
                    ),
                    EventStatus(
                        "456",
                        "Aprovado"
                    ),
                    EventStatus(
                        "789",
                        "Completo"
                    ),
                )

                val sampleEvents = listOf(
                    Event(
                        eventID = "1",
                        userId = "u1",
                        name = "Sunset Festival",
                        description = "A great outdoor event.",
                        startAt = Date(),
                        endAt = Date(),
                        price = 15.0,
                        createdAt = Date(),
                        eventStatus = eventStatus[1],
                        eventstatus_id = "1",
                        addressEvents = listOf(addressEvent[0], addressEvent[1]),
                        eventPicture = null
                    ),
                    Event(
                        eventID = "2",
                        userId = "u2",
                        name = "Tech Meetup",
                        description = "Networking for developers.",
                        startAt = Date(),
                        endAt = Date(),
                        price = 0.0,
                        createdAt = Date(),
                        eventStatus = eventStatus[1],
                        eventstatus_id = "2",
                        addressEvents = listOf(addressEvent[1], addressEvent[2]),
                        eventPicture = null
                    ),
                    Event(
                        eventID = "2",
                        userId = "u2",
                        name = "Tech Meetup",
                        description = "Networking for developers.",
                        startAt = Date(),
                        endAt = Date(),
                        price = 0.0,
                        createdAt = Date(),
                        eventStatus = eventStatus[1],
                        eventstatus_id = "2",
                        addressEvents = listOf(addressEvent[1], addressEvent[2]),
                        eventPicture = null
                    ),
                    Event(
                        eventID = "2",
                        userId = "u2",
                        name = "Tech Meetup",
                        description = "Networking for developers.",
                        startAt = Date(),
                        endAt = Date(),
                        price = 0.0,
                        createdAt = Date(),
                        eventStatus = eventStatus[1],
                        eventstatus_id = "2",
                        addressEvents = listOf(addressEvent[1], addressEvent[2]),
                        eventPicture = null
                    )
                )
                HomeScreen(sampleEvents)
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