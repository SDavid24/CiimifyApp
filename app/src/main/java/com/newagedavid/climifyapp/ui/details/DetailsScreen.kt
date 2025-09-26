package com.newagedavid.climifyapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(city: String, temp: Double?, desc: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("City: $city", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Hello", style = MaterialTheme.typography.headlineSmall)
        Text("Weather: $desc", style = MaterialTheme.typography.bodyLarge)
    }
}
