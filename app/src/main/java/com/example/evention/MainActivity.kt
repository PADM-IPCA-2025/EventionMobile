package com.example.evention

import SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.evention.mock.MockData
import com.example.evention.ui.screens.home.details.EventDetails
import com.example.evention.ui.theme.EventionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventionTheme {
                val event = MockData.events.first()
                //EventDetails(event)
                SearchScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {

    }
}