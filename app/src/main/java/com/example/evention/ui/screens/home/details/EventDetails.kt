package com.example.evention.ui.screens.home.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evention.mock.MockData
import com.example.evention.mock.MockUserData
import com.example.evention.model.Event
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val localDate = date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH)
    return localDate.format(formatter)
}

fun formatTime(date: Date): String {
    val localDateTime = date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("EEEE, hh:mm a", Locale.ENGLISH)
    return localDateTime.format(formatter)
}

@Composable
fun getDrawableId(name: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

@Composable
fun EventDetails(event: Event, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            if (event.eventPicture != "") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = getDrawableId(event.eventPicture!!)),
                        contentDescription = "Imagem do Evento",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            color = Color.Gray
                        )
                )
            }

            Text(
                text = event.name,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 18.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFEEF0FF), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Calendar",
                        tint = EventionBlue,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formatDate(event.startAt),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatTime(event.endAt),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFEEF0FF), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = EventionBlue,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.addressEvents[0].localtown,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = event.addressEvents[0].road,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFEEF0FF), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person",
                        tint = EventionBlue,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = MockUserData.users.find { it.userID == event.userId }?.username ?: "Desconhecido",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = MockUserData.users.find { it.userID == event.userId }?.userType?.type ?: "Desconhecido",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = EventionBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "4.8", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text(
                text = "About Event",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 25.dp)
            )
        }

        item {
            Button(
                onClick = { /* TODO: ação de comprar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EventionBlue),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "BUY TICKET ${event.price}€",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        val event = MockData.events.first()
        EventDetails(event)
    }
}