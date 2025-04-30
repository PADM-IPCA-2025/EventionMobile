package com.example.evention.ui.components.userEdit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evention.model.User
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.theme.EventionBlue

@Composable
fun UserEditInfo(user: User){
    if (user.profilePicture != null) {
        val drawableId = getDrawableId(user.profilePicture)
        if (drawableId != 0) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "User Profile Image",
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { /* TODO: Edit profile action */ },
        modifier = Modifier
            .size(170.dp, 30.dp)
            .border(1.dp, EventionBlue, RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = "CHANGE AVATAR",
            color = EventionBlue,
            fontWeight = FontWeight.Bold
        )
    }
}