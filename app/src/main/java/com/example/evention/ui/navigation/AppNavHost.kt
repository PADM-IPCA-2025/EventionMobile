package com.example.evention.ui.navigation

import SearchScreen
import SplashScreen
import UserPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evention.data.remote.authentication.RequireAuth
import com.example.evention.ui.screens.home.HomeScreen
import com.example.evention.ui.screens.home.details.EventDetails
import com.example.evention.ui.screens.home.notifications.NotificationScreen
import com.example.evention.ui.screens.home.payment.PaymentScreen
import com.example.evention.mock.MockData
import com.example.evention.mock.MockUserData
import com.example.evention.mock.TicketMockData
import com.example.evention.model.Event
import com.example.evention.model.User
import com.example.evention.ui.screens.auth.login.LoginScreen
import com.example.evention.ui.screens.auth.register.RegisterScreen
import com.example.evention.ui.screens.event.create.CreateEventScreen
import com.example.evention.ui.screens.home.HomeScreenViewModel
import com.example.evention.ui.screens.home.payment.PaymentViewModel
import com.example.evention.ui.screens.ticket.TicketDetailsPreview
import com.example.evention.ui.screens.ticket.TicketDetailsScreen
import com.example.evention.ui.screens.ticket.TicketScreenViewModel
import com.example.evention.ui.screens.profile.admin.AdminMenu
import com.example.evention.ui.screens.profile.admin.editEvent.EditEvent
import com.example.evention.ui.screens.profile.admin.events.AllEvents
import com.example.evention.ui.screens.profile.admin.events.EventsToApprove
import com.example.evention.ui.screens.profile.admin.events.EventsToApproveViewModel
import com.example.evention.ui.screens.profile.admin.events.EventsViewModel
import com.example.evention.ui.screens.profile.admin.users.AllUsers
import com.example.evention.ui.screens.profile.admin.users.UsersViewModel
import com.example.evention.ui.screens.profile.user.ScanQRCodeScreen
import com.example.evention.ui.screens.profile.user.userEdit.UserEdit
import com.example.evention.ui.screens.profile.user.userProfile.UserProfile
import com.example.evention.ui.screens.profile.userEvents.UserEvents
import com.example.evention.ui.screens.profile.userEvents.UserEventsViewModel
import com.example.evention.ui.screens.ticket.TicketFeedbackScreen
import com.example.evention.ui.screens.ticket.TicketsScreen
import com.google.gson.Gson

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("signIn") {
            LoginScreen(navController = navController)
        }
        composable("signUp") {
            RegisterScreen(navController = navController)
        }
        composable("home") {
            val viewModel: HomeScreenViewModel = viewModel()
            val events by viewModel.events.collectAsState()

            HomeScreen(events = events, navController = navController)
        }
        composable("search") {
            val viewModel: HomeScreenViewModel = viewModel()
            val events by viewModel.events.collectAsState()
            SearchScreen(events, navController = navController)
        }
        composable("create"){
            val userPrefs = remember { UserPreferences(context) }

            RequireAuth(navController, userPrefs) {
                CreateEventScreen(navController = navController)
            }

            CreateEventScreen(navController = navController)
        }
        composable("tickets"){
//            val viewModel: TicketScreenViewModel = viewModel()
//            val tickets by viewModel.tickets.collectAsState()

            val userPrefs = remember { UserPreferences(context) }

            RequireAuth(navController, userPrefs) {
                TicketsScreen(TicketMockData.tickets, navController = navController)
            }

            //TicketsScreen(TicketMockData.tickets, navController = navController) // TicketMockData.tickets
        }
        composable(
            "ticketDetails/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")
            TicketDetailsScreen(ticketId = ticketId ?: "", navController)
        }
        composable(
            "ticketFeedback/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")
            TicketFeedbackScreen(ticketId = ticketId ?: "", navController)
        }
        composable("profile"){

            val userPrefs = remember { UserPreferences(context) }

            RequireAuth(navController, userPrefs) {
                UserProfile(navController = navController)
            }

            //UserProfile(MockUserData.users.first().userID, navController = navController)
        }
        composable("adminMenu") {
            AdminMenu(navController)
        }
        composable("allUsers") {
            val viewModel: UsersViewModel = viewModel()
            val users by viewModel.users.collectAsState()
            AllUsers(users = users, navController)
        }
        composable("allEvents") {
            val viewModel: EventsViewModel = viewModel()
            val events by viewModel.events.collectAsState()
            AllEvents(events = events, navController)
        }
        composable("approveEvents") {
            val viewModel: EventsToApproveViewModel = viewModel()
            val events by viewModel.events.collectAsState()
            EventsToApprove(events = events, navController)
        }
        composable("userEvents") {
            val viewModel: UserEventsViewModel = viewModel()
            val events by viewModel.events.collectAsState()
            UserEvents(events = events, navController)
        }
        composable(
            "userEdit/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val user = Gson().fromJson(userJson, User::class.java)
            UserEdit(userToEdit = user, navController = navController)
        }
        composable(
            "eventEdit/{eventJson}",
            arguments = listOf(navArgument("eventJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventJson = backStackEntry.arguments?.getString("eventJson")
            val event = Gson().fromJson(eventJson, Event::class.java)
            EditEvent(eventToEdit = event, navController = navController)
        }

        composable("scanQRCode"){
            ScanQRCodeScreen(navController = navController)
        }
        composable("notifications") {
            NotificationScreen(notifications = listOf(), navController = navController)
        }
        composable(
            "eventDetails/{eventJson}",
            arguments = listOf(navArgument("eventJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventJson = backStackEntry.arguments?.getString("eventJson")
            val event = Gson().fromJson(eventJson, Event::class.java)
            EventDetails(eventDetails = event, navController = navController)
        }
        composable(
            "payment/{eventJson}/{ticketId}",
            arguments = listOf(
                navArgument("eventJson") { type = NavType.StringType },
                navArgument("ticketId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel: PaymentViewModel = viewModel()
            val eventJson = backStackEntry.arguments?.getString("eventJson")
            val ticketId = backStackEntry.arguments?.getString("ticketId") ?: ""

            val event = Gson().fromJson(eventJson, Event::class.java)
            PaymentScreen(event = event, ticketId = ticketId, navController = navController, viewModel)
        }
    }
}
