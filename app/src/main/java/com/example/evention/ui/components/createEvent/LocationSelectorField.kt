package com.example.evention.ui.components.createEvent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

@Composable
fun LocationSelectorField(
    labelText: String,
    selectedLocation: LatLng?,
    onClick: () -> Unit
) {
    val displayText = if (selectedLocation != null) {
        "Lat: %.5f, Lng: %.5f".format(selectedLocation.latitude, selectedLocation.longitude)
    } else {
        "Tap to select location"
    }

    OutlinedTextField(
        value = displayText,
        onValueChange = {},
        label = { Text(labelText) },
        readOnly = true,
        enabled = false,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Black,
            disabledBorderColor = Color.Black
        )
    )
}
