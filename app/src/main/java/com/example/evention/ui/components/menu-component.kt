package com.example.evention.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.annotation.DrawableRes
import androidx.compose.ui.tooling.preview.Preview
import com.example.evention.R
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme

@Composable
fun MenuComponent(currentPage: String, onMenuClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(
            name = "Home",
            iconRes = if (currentPage == "Home") R.drawable.home_filled else R.drawable.home,
            currentPage = currentPage,
            onClick = onMenuClick
        )
        MenuItem(
            name = "Search",
            iconRes = if (currentPage == "Search") R.drawable.search_filled else R.drawable.search,
            currentPage = currentPage,
            onClick = onMenuClick
        )
        MenuItem(
            name = "Create",
            iconRes = if (currentPage == "Create") R.drawable.add_filled else R.drawable.add,
            currentPage = currentPage,
            onClick = onMenuClick
        )
        MenuItem(
            name = "Tickets",
            iconRes = if (currentPage == "Tickets") R.drawable.ticket_filled else R.drawable.ticket,
            currentPage = currentPage,
            onClick = onMenuClick
        )
        MenuItem(
            name = "Profile",
            iconRes = if (currentPage == "Profile") R.drawable.profile_filled else R.drawable.profile_circle,
            currentPage = currentPage,
            onClick = onMenuClick
        )
    }
}

@Composable
fun MenuItem(
    name: String,
    @DrawableRes iconRes: Int,
    currentPage: String,
    onClick: (String) -> Unit
) {
    val isSelected = name == currentPage
    val textColor = if (isSelected) EventionBlue else Color(0xFF3E3E3E)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick(name) }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = name,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    EventionTheme {
        MenuComponent(currentPage = "Search", onMenuClick = {})
    }
}
