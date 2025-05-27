package com.example.evention.ui.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evention.ui.screens.home.HomeScreen
import com.example.evention.ui.screens.home.details.EventDetails
import com.example.evention.ui.screens.home.notifications.NotificationScreen
import com.example.evention.ui.screens.home.payment.PaymentScreen
import com.example.evention.mock.MockData
import com.example.evention.mock.MockUserData
import com.example.evention.mock.TicketMockData
import com.example.evention.ui.screens.event.create.CreateEventScreen
import com.example.evention.ui.screens.home.HomeScreenViewModel
import com.example.evention.ui.screens.ticket.TicketDetailsPreview
import com.example.evention.ui.screens.ticket.TicketDetailsScreen
import com.example.evention.ui.screens.ticket.TicketScreenViewModel
import com.example.evention.ui.screens.profile.admin.AdminMenu
import com.example.evention.ui.screens.profile.admin.editEvent.EditEvent
import com.example.evention.ui.screens.profile.admin.events.AllEvents
import com.example.evention.ui.screens.profile.admin.events.EventsToApprove
import com.example.evention.ui.screens.profile.admin.users.AllUsers
import com.example.evention.ui.screens.profile.user.userEdit.UserEdit
import com.example.evention.ui.screens.profile.user.userProfile.UserProfile
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
//            val viewModel: TicketScreenViewModel = viewModel()
//            val tickets by viewModel.tickets.collectAsState()
            TicketsScreen(TicketMockData.tickets, navController = navController) // TicketMockData.tickets
        }
        composable(
            "ticketDetails/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")
            TicketDetailsScreen(ticketId = ticketId ?: "", navController)
        }
        composable("profile"){
            UserProfile(MockUserData.users.first().userID, navController = navController)
        }
        composable("adminMenu") {
            AdminMenu(navController)
        }
        composable("allUsers") {
            //val viewModel: UsersViewModel = viewModel()
            //val users by viewModel.users.collectAsState()
            AllUsers(users = MockUserData.users, navController)
        }
        composable("allEvents") {
            //val viewModel: EventsViewModel = viewModel()
            //val events by viewModel.events.collectAsState()
            AllEvents(events = MockData.events, navController)
        }
        composable("approveEvents") {
            //val viewModel: EventsToApproveViewModel = viewModel()
            //val events by viewModel.events.collectAsState()
            EventsToApprove(events = MockData.events.filter { event -> event.eventStatus.status == "Pendente" }, navController)
        }
        composable("userEvents") {
            AllEvents(MockData.events.filter { event -> event.userId == MockUserData.users.first().userID }, navController)
        }
        composable("userEdit/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType})
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            UserEdit(userId = userId ?: "", navController)
        }
        composable("eventEdit/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType})
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EditEvent(eventId = eventId?: "", navController)
        }

        composable("notifications") {
            NotificationScreen(notifications = listOf(), navController = navController)
        }
        composable(
            "eventDetails/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetails(eventId = eventId ?: "", navController = navController)
        }
        composable(
            "payment/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            PaymentScreen(eventId = eventId ?: "", navController)
        }
    }
}
