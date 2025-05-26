package com.example.evention.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evention.mock.MockData
import com.example.evention.model.Event
import com.example.evention.ui.components.home.EventCard
import com.example.evention.ui.components.home.FilterButton
import com.example.evention.ui.components.home.HomeSearch
import com.example.evention.ui.theme.EventionTheme
import com.example.evention.ui.components.MenuComponent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(events: List<Event>, navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            MenuComponent(
                currentPage = "Home",
                onMenuClick = { /* navegação */ }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 30.dp),
        ) {
            item {
                HomeSearch(navController = navController)
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Upcoming Events",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    FilterButton()
                }
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
            }

            items(events) { event ->
                EventCard(
                    event = event,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 8.dp),
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    EventionTheme {

        /*
        // Eventos da API
        val viewModel: HomeScreenViewModel = viewModel()
        val eventsApi by viewModel.events.collectAsState()
        */

        val navController = rememberNavController()
        HomeScreen(events = MockData.events, navController = navController)
    }
}