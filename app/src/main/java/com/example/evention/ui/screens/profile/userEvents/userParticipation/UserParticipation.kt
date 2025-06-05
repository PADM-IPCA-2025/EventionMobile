package com.example.evention.ui.screens.profile.userEvents.userParticipation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.evention.mock.MockData
import com.example.evention.model.Event
import com.example.evention.model.User
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.theme.EventionTheme
import java.text.SimpleDateFormat
import java.util.Locale

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
                ParticipantCard(
                    user = item.user,
                    ticketDate = item.ticket.createdAt
                )
            }
        }
    }
}

@Composable
fun ParticipantCard(
    user: User,
    ticketDate: String,
    modifier: Modifier = Modifier
) {
    val imageUrl = user.profilePicture?.let { "https://10.0.2.2:5010/user$it" }
    var hasError by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            if (user.profilePicture == null || hasError) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    onError = { hasError = true }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(ticketDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
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