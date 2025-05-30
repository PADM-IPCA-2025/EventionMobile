package com.example.evention.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evention.model.User
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.theme.EventionBlue

@Composable
fun UserInfo(user: User, navController: NavController){
    if (user.profilePicture != null) {
        val drawableId = getDrawableId(user.profilePicture)
        if (drawableId != 0) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "User Profile Image",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = user.username,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    /*Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 5.dp)
    ) {
        repeat(5) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Text(
        text = "(110)",
        style = MaterialTheme.typography.bodyLarge
    )*/

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            navController.navigate("userEdit/${user.userID}")
        },
        modifier = Modifier
            .size(154.dp, 50.dp)
            .border(1.dp, EventionBlue, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = "Edit Profile",
            color = EventionBlue,
            fontWeight = FontWeight.Bold
        )
    }
}