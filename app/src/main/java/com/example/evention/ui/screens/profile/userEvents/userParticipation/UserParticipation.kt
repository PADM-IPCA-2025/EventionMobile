package com.example.evention.ui.screens.profile.userEvents.userParticipation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.mock.MockData
import com.example.evention.model.Event
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.screens.profile.userEvents.UserEventsViewModel
import com.example.evention.ui.theme.EventionTheme

@Composable
fun UserParticipation(
    event: Event,
    navController: NavController,
    viewModel: UserParticipationViewModel = viewModel()
) {
    LaunchedEffect(event.eventID) {
        viewModel.loadTickets(event.eventID)
    }

    val ticketWithUsers by viewModel.ticketsWithUsers.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 18.dp)
    ) {
        TitleComponent("Participants", true, navController)

        Spacer(modifier = Modifier.height(16.dp))

        if (ticketWithUsers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No participations yet.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {

            ticketWithUsers.forEach { item ->
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column {
                        Text(text = "User: ${item.user.username}")
                        Text(text = "Email: ${item.user.email}")
                        Text(text = "Ticket ID: ${item.ticket.createdAt}")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserParticipationPreview() {
    EventionTheme {
        val navController = rememberNavController()
        UserParticipation(MockData.events.first(), navController = navController)
    }
}