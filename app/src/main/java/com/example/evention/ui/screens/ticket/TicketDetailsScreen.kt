package com.example.evention.ui.screens.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.evention.R
import com.example.evention.mock.MockData
import com.example.evention.mock.TicketMockData
import com.example.evention.model.Ticket
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.evention.ui.screens.home.details.EventDetailsViewModel
import generateQrCodeBitmap
import java.time.ZoneId

@Composable
fun TicketDetailsScreen(ticketId: String, navController: NavController, viewModel: TicketDetailsScreenViewModel = viewModel()) {


    LaunchedEffect(ticketId) {
        viewModel.loadTicketById(ticketId)
    }
    //val ticketNullable by viewModel.ticket.collectAsState()
    val ticketNullable = TicketMockData.tickets.find { ticket -> ticket.ticketID == ticketId }

    ticketNullable?.let { ticket ->

        val qrBitmap = remember(ticket.ticketID) {
            generateQrCodeBitmap(ticket.ticketID)
        }

        val eventEndAtLocalDateTime = ticket.event.endAt.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 28.dp)
    ) {

        TitleComponent("Ticket Details", true, navController)

        // Nome do evento
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = ticket.event.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Calendário + data e hora
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                modifier = Modifier.size(28.dp),
                tint = EventionBlue
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = ticket.event.startAt.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = ticket.event.endAt.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        // Localização
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(28.dp),
                tint = EventionBlue
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = ticket.event.addressEvents[0].road,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = ticket.event.addressEvents[0].localtown,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        // QR Code
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = qrBitmap.asImageBitmap(),    // painter = painterResource(id = R.drawable.qrcodemock),
                contentDescription = "QR Code",
                modifier = Modifier.size(180.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (LocalDateTime.now().isAfter(eventEndAtLocalDateTime)) {
        Button(
            onClick = {  navController.navigate("ticketFeedback/${ticketId}") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EventionBlue),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Give Feedback",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketDetailsPreview() {
    EventionTheme {
        TicketDetailsScreen(TicketMockData.tickets[0].ticketID, navController = rememberNavController())
    }
}
