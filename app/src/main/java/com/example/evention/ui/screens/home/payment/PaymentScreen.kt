package com.example.evention.ui.screens.home.payment

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme

@Composable
fun PaymentScreen(paymentMethods: List<PaymentMethod>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 18.dp)
    ) {

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
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(28.dp))
        }

        Text(
            text = "Payment",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold // Subtítulo em negrito
        )

        LazyColumn {
            items(paymentMethods.size) { index ->
                val paymentMethod = paymentMethods[index]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(12.dp)
                    ) {
                        /*Icon(
                            imageVector = paymentMethod.icon,
                            contentDescription = paymentMethod.name,
                            modifier = Modifier.fillMaxSize()
                        )*/
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = paymentMethod.method,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight= FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Checkbox(
                        checked = paymentMethod.isSelected,
                        onCheckedChange = { isChecked ->
                            paymentMethod.isSelected = isChecked
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "120 €",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: ação de finalizar pagamento */ },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = EventionBlue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "PAY",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        PaymentScreen(listOf(
            PaymentMethod("Paypal", true),
            PaymentMethod("Credit Card", false)
        ))
    }
}