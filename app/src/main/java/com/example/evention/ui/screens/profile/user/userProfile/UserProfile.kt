package com.example.evention.ui.screens.profile.user.userProfile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.mock.MockData
import com.example.evention.ui.components.MenuComponent
import com.example.evention.ui.screens.home.details.EventDetailsViewModel

@Composable
fun UserProfile(navController: NavController, viewModel: UserProfileViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }
    val userNullable by viewModel.user.collectAsState()

    userNullable?.let { user ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,
            bottomBar = {
                MenuComponent(
                    currentPage = "Profile",
                    navController = navController
                )
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp, vertical = 18.dp)
                    .padding(innerPadding)
            ) {

                TitleComponent("Profile", false, navController)

                UserInfo(user, navController = navController)

                Spacer(modifier = Modifier.height(32.dp))

                MenuCard(navController)
            }
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