package com.example.evention.ui.navigation

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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            //val viewModel: HomeScreenViewModel = viewModel()
            //val events by viewModel.events.collectAsState()

            HomeScreen(events = MockData.events, navController = navController)
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
