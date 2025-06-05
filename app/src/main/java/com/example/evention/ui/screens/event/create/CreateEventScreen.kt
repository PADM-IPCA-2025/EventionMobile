package com.example.evention.ui.screens.event.create

import CustomCreateEventTextField
import UserPreferences
import android.location.Geocoder
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.evention.ui.components.MenuComponent
import com.example.evention.ui.components.createEvent.CustomDateRangeTextField
import com.example.evention.ui.components.createEvent.LocationSelectorField
import com.example.evention.ui.components.home.FilterButtonWithDateRange
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

//fun CreateEventScreen(navController: NavController, viewModel: CreateEventViewModel = viewModel()) {

// Para permitir salvar LatLng com rememberSaveable
val LatLngSaver: Saver<LatLng?, *> = Saver(
    save = { listOf(it?.latitude, it?.longitude) },
    restore = {
        val lat = it[0] as? Double
        val lng = it[1] as? Double
        if (lat != null && lng != null) LatLng(lat, lng) else null
    }
)

@Composable
fun CreateEventScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val viewModel = remember { CreateEventViewModel(userPreferences) }
    val createEventState by viewModel.createEventState.collectAsState()

    val selectedStartDate = rememberSaveable { mutableStateOf<Date?>(null) }
    val selectedEndDate = rememberSaveable { mutableStateOf<Date?>(null) }
    val showDatePicker = rememberSaveable { mutableStateOf(false) }

    val eventName = rememberSaveable { mutableStateOf("") }
    val eventDescription = rememberSaveable { mutableStateOf("") }
    val eventPrice = rememberSaveable { mutableStateOf("") }

    val selectedLocation = rememberSaveable(stateSaver = LatLngSaver) { mutableStateOf<LatLng?>(null) }
    val addressText = rememberSaveable { mutableStateOf("") }

    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    // ✅ Recebe localização da SelectLocationScreen
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val navLocation = currentBackStackEntry?.savedStateHandle?.get<LatLng>("selectedLocation")
    LaunchedEffect(navLocation) {
        navLocation?.let {
            selectedLocation.value = it
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val fullAddress = buildString {
                    append(address.thoroughfare ?: "")
                    if (address.subThoroughfare != null) append(", ${address.subThoroughfare}")
                    if (address.postalCode != null) append(", ${address.postalCode}")
                    if (address.locality != null) append(", ${address.locality}")
                }
                addressText.value = fullAddress
                currentBackStackEntry.savedStateHandle.remove<LatLng>("selectedLocation")
            }
        }
    }

    // ✅ Navegação e Toast em caso de sucesso ou erro
    LaunchedEffect(createEventState) {
        when (val state = createEventState) {
            is CreateEventState.Success -> {
                Toast.makeText(context, "Event created successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate("userEvents") {
                    popUpTo("create") { inclusive = true }
                }
            }
            is CreateEventState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            MenuComponent(currentPage = "Create", navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 18.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComponent("Create Event", false, navController)

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.blue_camera),
                        contentDescription = "Blue camera icon",
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CHANGE", fontSize = 12.sp, color = EventionBlue)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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
                FilterButtonWithDateRange { startMillis, endMillis ->
                    if (startMillis != null && endMillis != null) {
                        selectedStartDate.value = Date(startMillis)
                        selectedEndDate.value = Date(endMillis)
                    }
                    showDatePicker.value = false
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            CustomCreateEventTextField("Event Name", eventName.value) {
                eventName.value = it
            }

            CustomCreateEventTextField("Description", eventDescription.value) {
                eventDescription.value = it
            }

            LocationSelectorField(
                labelText = "Event Location",
                selectedLocation = selectedLocation.value,
                displayAddress = addressText.value,
                onClick = { navController.navigate("selectLocation") }
            )

            CustomCreateEventTextField("Price", eventPrice.value) {
                eventPrice.value = it
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val name = eventName.value
                    val description = eventDescription.value
                    val price = eventPrice.value.toDoubleOrNull()
                    val start = selectedStartDate.value
                    val end = selectedEndDate.value
                    val location = selectedLocation.value

                    if (name.isNotBlank() && description.isNotBlank() && start != null && end != null && location != null && price != null) {
                        viewModel.createEvent(
                            name, description, start, end, price, location, context
                        )
                    } else {
                        Toast.makeText(context, "Incorrect fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EventionBlue)
            ) {
                if (createEventState is CreateEventState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Create Event", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
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