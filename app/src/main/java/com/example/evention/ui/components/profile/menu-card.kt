package com.example.evention.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuCard(){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            ProfileMenuItem(icon = Icons.Outlined.Person, label = "Admin Menu", sublabel = "Manage users and events")
            ProfileMenuItem(icon = Icons.Outlined.DateRange, label = "My Events", sublabel = "Manage and explore your events")
            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                label = "Notifications",
                isNotification = true, sublabel = "Turn on/off your notifications"
            )
            ProfileMenuItem(icon = Icons.Outlined.Build, label = "Change Password", sublabel = "Change your password")
            ProfileMenuItem(icon = Icons.Outlined.Info, label = "Logout", sublabel = "Log out of your account")
        }
    }
}