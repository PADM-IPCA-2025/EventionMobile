package com.example.evention.ui.screens.event.create

import CustomCreateEventTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evention.R
import com.example.evention.ui.components.TitleComponent
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.evention.ui.components.MenuComponent
import com.example.evention.ui.components.createEvent.CustomDateRangeTextField
import com.example.evention.ui.components.home.FilterButtonWithDateRange
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

//fun CreateEventScreen(navController: NavController, viewModel: CreateEventViewModel = viewModel()) {

@Composable
fun CreateEventScreen(navController: NavController) {
    val selectedStartDate = remember { mutableStateOf<Date?>(null) }
    val selectedEndDate = remember { mutableStateOf<Date?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }

    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            MenuComponent(
                currentPage = "Create",
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 18.dp)
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComponent("Create Event", false, navController)

            // Caixa de upload de imagem
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(1.dp, EventionBlue, RoundedCornerShape(15.dp))
                    .background(Color(0xFF8296AA).copy(alpha = 0.1f), RoundedCornerShape(15.dp))
                    .height(180.dp)
                    .clickable { /* TODO: Upload image */ },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
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
                        color = Color(0xFF0081FF),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Campo de duração (com calendário)
            CustomDateRangeTextField(
                labelText = "Event Duration",
                startDate = selectedStartDate.value,
                endDate = selectedEndDate.value,
                formatter = formatter,
                onDateRangeSelected = { start, end ->
                    selectedStartDate.value = start
                    selectedEndDate.value = end
                }
            )


            if (showDatePicker.value) {
                FilterButtonWithDateRange(
                    onDateRangeSelected = { startMillis: Long?, endMillis: Long? ->
                        if (startMillis != null && endMillis != null) {
                            selectedStartDate.value = Date(startMillis)
                            selectedEndDate.value = Date(endMillis)
                        }
                        showDatePicker.value = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Campos de texto
            CustomCreateEventTextField("Event Name")
            CustomCreateEventTextField("Description")
            CustomCreateEventTextField("Address")
            CustomCreateEventTextField("Price")

            Spacer(modifier = Modifier.height(20.dp))

            // Botão de criação
            Button(
                onClick = {
                    val start = selectedStartDate.value
                    val end = selectedEndDate.value
                    if (start != null && end != null) {
//                        viewModel.createEvent(
//                            startDate = start,
//                            endDate = end
//                            // adicione os outros campos aqui
//                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0081FF))
            ) {
                Text(
                    text = "Create Event",
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
fun Preview() {
    EventionTheme {
        val navController = rememberNavController()
        CreateEventScreen(navController = navController)
    }
}