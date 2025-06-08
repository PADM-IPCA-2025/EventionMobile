package com.example.evention.ui.components.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.evention.mock.MockUserData
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.screens.home.notifications.NotificationItem

@Composable
fun NotificationRow(notification: NotificationItem) {
    val user = MockUserData.users.find { it.userID == notification.userId }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        if (user?.profilePicture != null) {
            val drawableId = getDrawableId(user.profilePicture)
            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "User Profile Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }
        } else {
            Image(
                painter = painterResource(id = com.example.evention.R.drawable.logosplash),
                contentDescription = "User Profile Image",
                modifier = Modifier
                    .size(48.dp)
//                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        if (user != null) {
                            append(user.username)
                        }
                    }
                    append(" ${notification.message}")
                },
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = notification.time,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}