package com.example.evention.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FilterButtonWithDateRange() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedStartDate by remember { mutableStateOf<Long?>(null) }
    var selectedEndDate by remember { mutableStateOf<Long?>(null) }

    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Column {
        FilterButton(onClick = { showDatePicker = true })

        if (selectedStartDate != null && selectedEndDate != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Selecionado: ${formatter.format(Date(selectedStartDate!!))} até ${formatter.format(Date(selectedEndDate!!))}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (showDatePicker) {
            DateRangePickerModal(
                onDateRangeSelected = { start, end ->
                    selectedStartDate = start
                    selectedEndDate = end
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}

@Composable
fun FilterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0081FF),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Filter",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Filters")
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
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(text = "Selecionar intervalo de datas")
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

