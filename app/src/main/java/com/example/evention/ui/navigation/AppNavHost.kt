package com.example.evention.ui.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evention.ui.screens.home.HomeScreen
import com.example.evention.ui.screens.home.HomeScreenViewModel
import com.example.evention.ui.screens.home.details.EventDetails
import com.example.evention.ui.screens.home.notifications.NotificationScreen
import com.example.evention.ui.screens.home.payment.PaymentScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evention.mock.MockData
import com.example.evention.mock.MockUserData
import com.example.evention.mock.TicketMockData
import com.example.evention.ui.screens.event.create.CreateEventScreen
import com.example.evention.ui.screens.profile.user.UserProfile
import com.example.evention.ui.screens.ticket.TicketScreenViewModel
import com.example.evention.ui.screens.ticket.TicketsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            //val viewModel: HomeScreenViewModel = viewModel()
            //val events by viewModel.events.collectAsState()

            HomeScreen(events = MockData.events, navController = navController)
        }
        composable("search"){
            SearchScreen(navController = navController)
        }
        composable("create"){
            CreateEventScreen(navController = navController)
        }
        composable("tickets"){
            val viewModel: TicketScreenViewModel = viewModel()
            val tickets by viewModel.tickets.collectAsState()
            TicketsScreen(tickets, navController = navController) // TicketMockData.tickets
        }
        composable("profile"){
            UserProfile(MockUserData.users.first(), navController = navController)
        }

        composable("notifications") {
            NotificationScreen(notifications = listOf())
        }
        composable(
            "eventDetails/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetails(eventId = eventId ?: "")
        }
        composable(
            "payment/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            PaymentScreen(eventId = eventId ?: "")
        }
    }
}
