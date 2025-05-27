package com.example.evention.ui.components.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.evention.ui.theme.EventionBlue
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FilterButtonWithDateRange() {
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    FilterButton(onClick = { showDatePicker = true })

    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { start, end ->
                if (start != null && end != null) {
                    Log.d("DateRange", "Selected: ${formatter.format(Date(start))} to ${formatter.format(Date(end))}")
                }
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun FilterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = EventionBlue,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Filter",
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (startDate: Long?, endDate: Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        dateRangePickerState.selectedStartDateMillis,
                        dateRangePickerState.selectedEndDateMillis
                    )
                    onDismiss()
                }
            ) {
                Text("Confirm", color = EventionBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        },
        colors = DatePickerDefaults.colors(
            titleContentColor = EventionBlue,
            headlineContentColor = EventionBlue,
            weekdayContentColor = EventionBlue,
            selectedDayContainerColor = EventionBlue,
            selectedDayContentColor = Color.White
        )
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(text = "Select date range")
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
