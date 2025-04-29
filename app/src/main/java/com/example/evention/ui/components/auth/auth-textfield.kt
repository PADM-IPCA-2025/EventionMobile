package com.example.evention.ui.components.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.evention.R

@Composable
fun AuthTextField(
    placeholderText: String,
    iconResId: Int,
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                color = Color(0xFF747688)
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = placeholderText,
                modifier = Modifier.size(24.dp)
            )
        },
        //textStyle = TextStyle(
        //    color = Color(0xFF747688), // Cor do texto
        //    fontSize = 16.sp,
        //    fontWeight = FontWeight.Normal
        //),
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFE4DFDF),
            unfocusedIndicatorColor = Color(0xFFE4DFDF),
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF)
        )
    )
}
