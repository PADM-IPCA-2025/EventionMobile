package com.example.evention.ui.screens.profile.user.userEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.mock.MockUserData
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.components.userEdit.LabeledTextField
import com.example.evention.ui.components.userEdit.UserEditInfo
import com.example.evention.ui.screens.profile.user.userProfile.UserProfileViewModel
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme

@Composable
fun UserEdit(userId: String, navController: NavController, viewModel: UserProfileViewModel = viewModel()) {
    /*LaunchedEffect(userId) {
        viewModel.loadUserById(userId)
    }
    val userNullable by viewModel.user.collectAsState()*/
    val userNullable = MockUserData.users.find { user -> user.userID == userId }

    userNullable?.let { user ->
        var username by remember { mutableStateOf(user.username) }
        var email by remember { mutableStateOf(user.email) }
        var phone by remember { mutableStateOf(user.phone ?: "") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            TitleComponent("Edit Profile", arrowBack = true, navController = navController)

            Spacer(modifier = Modifier.size(16.dp))

            UserEditInfo(user)

            Spacer(modifier = Modifier.size(24.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                LabeledTextField(
                    label = "Username",
                    value = username,
                    onValueChange = { username = it }
                )

                Spacer(modifier = Modifier.size(8.dp))

                LabeledTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.size(8.dp))

                LabeledTextField(
                    label = "Phone",
                    value = phone.toString(),
                    onValueChange = { phone = it }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO: Save changes */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EventionBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Save Changes",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserEditPreview() {
    EventionTheme {
        val navController = rememberNavController()
        UserEdit(MockUserData.users.first().userID, navController = navController)
    }
}