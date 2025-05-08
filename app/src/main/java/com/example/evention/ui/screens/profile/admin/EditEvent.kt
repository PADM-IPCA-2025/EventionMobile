package com.example.evention.ui.screens.profile.admin

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evention.R
import com.example.evention.mock.MockData
import com.example.evention.model.Event
import com.example.evention.ui.components.userEdit.LabeledTextField
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditEvent(event: Event) {
    var name by remember { mutableStateOf(event.name) }
    var description by remember { mutableStateOf(event.description) }
    var price by remember { mutableStateOf(event.price ?: "") }

    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    fun showDatePicker(initialDate: Calendar, onDateSelected: (String) -> Unit) {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                onDateSelected(dateFormat.format(calendar.time))
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                // TODO: ação de voltar
                            }
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Event Editor",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.size(28.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = "$startDate - $endDate",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val today = Calendar.getInstance()
                            showDatePicker(today) { date ->
                                startDate = date
                                showDatePicker(today) { end ->
                                    endDate = end
                                }
                            }
                        },
                    readOnly = true,
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.calendar_picker),
                            contentDescription = "Calendar logo",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = getDrawableId(event.eventPicture!!)),
                            contentDescription = "Imagem do Evento",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(RoundedCornerShape(14.dp))
                        )
                    }
                    Button(
                        onClick = { /* TODO: Change image */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 100.dp, height = 34.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.4f)
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.blue_camera),
                            contentDescription = "Blue camera icon",
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "CHANGE",
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = EventionBlue,
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                LabeledTextField(
                    label = "Event Name",
                    value = name,
                    onValueChange = { name = it }
                )

                Spacer(modifier = Modifier.size(10.dp))

                LabeledTextField(
                    label = "Description",
                    value = description,
                    onValueChange = { description = it }
                )

                Spacer(modifier = Modifier.size(10.dp))

                LabeledTextField(
                    label = "Price",
                    value = price.toString() + "€",
                    onValueChange = { price = it }
                )
            }

            Button(
                onClick = { /* Lógica para editar evento */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EventionBlue
                )
            ) {
                Text(
                    text = "Save Changes",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserEditPreview() {
    EventionTheme {
        EditEvent(MockData.events.first())
    }
}
