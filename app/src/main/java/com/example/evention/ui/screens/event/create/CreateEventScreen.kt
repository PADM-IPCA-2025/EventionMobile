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
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme

@Composable
fun CreateEventScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 18.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Create Event",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .border(
                    width = 1.dp,
                    color = EventionBlue,
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFF8296AA).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(15.dp)
                )
                .height(180.dp)
                .clickable {
                    // TODO: Ação para fazer upload da imagem
                },
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

        // Fields
        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)

        CustomCreateEventTextField("Event Duration")

        CustomCreateEventTextField("Event Name")

        CustomCreateEventTextField("Description")

        CustomCreateEventTextField("Address")

        CustomCreateEventTextField("Price")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /* Lógica para criar evento */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0081FF)
            )
        ) {

            Text(
                text = "Create Event",
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        CreateEventScreen()
    }
}