package com.example.evention.ui.components.admin.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.evention.model.Event
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.theme.EventionBlue
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val zonedDateTime = date.toInstant()
        .atZone(ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("MMM dd · h:mma", Locale.ENGLISH)
    return zonedDateTime.format(formatter)
}

@Composable
fun EventListRow(
    event: Event,
    firstSection: String,
    secondSection: String,
    onEdit: (Event) -> Unit,
    onRemove: (Event) -> Unit
) {
    val showMenu = firstSection.isNotBlank() || secondSection.isNotBlank()

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (event.eventPicture != null) {
                val drawableId = getDrawableId(event.eventPicture)
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = formatDate(event.startAt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = EventionBlue
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = event.addressEvents.getOrNull(0)?.road.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            if (showMenu) {
                var expanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mais opções")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (firstSection.isNotBlank()) {
                            DropdownMenuItem(
                                text = { Text(text = firstSection) },
                                onClick = {
                                    expanded = false
                                    onEdit(event)
                                }
                            )
                        }
                        if (secondSection.isNotBlank()) {
                            DropdownMenuItem(
                                text = { Text(text = secondSection) },
                                onClick = {
                                    expanded = false
                                    onRemove(event)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
