package com.example.evention.ui.screens.profile.user.userProfile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evention.mock.MockUserData
import com.example.evention.model.User
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.components.profile.MenuCard
import com.example.evention.ui.components.profile.UserInfo
import com.example.evention.ui.theme.EventionTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.mock.MockData
import com.example.evention.model.Feedback
import com.example.evention.model.FeedbackReputation
import com.example.evention.ui.components.MenuComponent
import com.example.evention.ui.screens.home.details.EventDetailsViewModel

@Composable
fun UserProfile(
    navController: NavController,
    userProfile: User? = null,
    viewModel: UserProfileViewModel = viewModel()
) {
    val userNullable by viewModel.user.collectAsState()
    val reputation by viewModel.reputation.collectAsState()
    val eventsMap by viewModel.events.collectAsState()

    val user = userProfile ?: userNullable

    LaunchedEffect(userProfile) {
        if (userProfile == null) {
            viewModel.loadUserProfile()
        } else {
            viewModel.loadUserReputation(userProfile.userID)
        }
    }

    user?.let {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,
            bottomBar = {
                if (userProfile == null) {
                    MenuComponent(
                        currentPage = "Profile",
                        navController = navController
                    )
                }
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp, vertical = 18.dp)
                    .padding(innerPadding)
            ) {
                TitleComponent("Profile", userProfile != null, navController)

                UserInfo(it, navController = navController, userProfile != null)

                reputation?.let { rep ->
                    Spacer(modifier = Modifier.height(12.dp))

                    RatingStars(rating = rep.reputation)

                    val feedbackEventCount = rep.tickets
                        .filter { it.feedback != null }
                        .map { it.event_id }
                        .distinct()
                        .count()

                    Text(
                        text = "($feedbackEventCount)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (userProfile == null) {
                    MenuCard(navController)
                } else {
                    Text(
                        text = "Feedbacks Received",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    LaunchedEffect(reputation) {
                        reputation?.tickets
                            ?.map { it.event_id }
                            ?.distinct()
                            ?.forEach { viewModel.loadEventById(it) }
                    }

                    val ticketsWithFeedback = reputation?.tickets?.filter { it.feedback != null }.orEmpty()

                    if (ticketsWithFeedback.isEmpty()) {
                        Text("No feedbacks received.", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        ticketsWithFeedback.forEach { ticket ->
                            val eventName = eventsMap[ticket.event_id]?.name ?: "A carregar..."

                            FeedbackRow(
                                feedback = ticket.feedback!!,
                                eventName = eventName
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingStars(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun FeedbackRow(
    feedback: FeedbackReputation,
    eventName: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Event: $eventName", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Rating: ${feedback.rating} ⭐", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(feedback.commentary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    EventionTheme {
        val navController = rememberNavController()
        UserProfile(navController = navController)
    }
}