package com.example.evention.ui.screens.profile.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evention.mock.MockUserData
import com.example.evention.model.User
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.components.profile.MenuCard
import com.example.evention.ui.components.profile.UserInfo
import com.example.evention.ui.theme.EventionTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.ui.components.MenuComponent

@Composable
fun UserProfile(user: User, navController: NavController) {
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

            TitleComponent("Profile", false)


            UserInfo(user)

            Spacer(modifier = Modifier.height(32.dp))

            MenuCard()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    EventionTheme {
        val navController = rememberNavController()
        UserProfile(MockUserData.users.first(), navController = navController)
    }
}