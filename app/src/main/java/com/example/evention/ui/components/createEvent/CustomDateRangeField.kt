package com.example.evention.ui.components.createEvent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evention.ui.components.home.DateRangePickerModal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@Composable
fun CustomDateRangeTextField(
    labelText: String,
    startDate: Date?,
    endDate: Date?,
    formatter: SimpleDateFormat,
    onDateRangeSelected: (Date, Date) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val displayText = if (startDate != null && endDate != null) {
        "${formatter.format(startDate)} - ${formatter.format(endDate)}"
    } else {
        ""
    }

    OutlinedTextField(
        value = displayText,
        onValueChange = {},
        readOnly = true,
        enabled = false,
        label = { Text(labelText, color = Color.Black) },
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { showDatePicker = true },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Black,
            disabledBorderColor = Color.Black,
        )
    )

    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { startMillis, endMillis ->
                if (startMillis != null && endMillis != null) {
                    onDateRangeSelected(Date(startMillis), Date(endMillis))
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


