package com.example.evention.ui.screens.home.notifications

import UserPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.R
import com.example.evention.data.remote.authentication.LoginViewModelFactory
import com.example.evention.notifications.NotificationViewModelFactory
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.components.notifications.NotificationRow
import com.example.evention.ui.screens.auth.login.LoginScreenViewModel
import com.example.evention.ui.theme.EventionTheme

@Composable
fun NotificationScreen( modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val viewModel: NotificationViewModel = viewModel(factory = NotificationViewModelFactory(userPrefs))

    val notifications = viewModel.notifications

    Log.e("NOT", notifications.toString())
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 18.dp)
    ) {

        TitleComponent("Notifications", true, navController)

        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nonotification),
                        contentDescription = "No notifications"
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "No notifications",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            LazyColumn {
                items(notifications.size) { index ->
                    NotificationRow(notification = notifications[index])
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NotificationPreview() {
//    EventionTheme {
//        NotificationScreen(
//            notifications = listOf(
//                NotificationItem("u1", "purchased a ticket for your event.", "1 hr ago"),
//                NotificationItem("u2", "joined your Gala Music Festival.", "9 hr ago"),
//                NotificationItem("u3", "invited you to the International Kids Safe.", "Tue, 5:10 pm")
//            ), navController = rememberNavController()
//        )
//    }
//}