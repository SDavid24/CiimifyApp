package com.newagedavid.climifyapp.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.newagedavid.climifyapp.ui.city.mapWeatherIcon
import com.newagedavid.climifyapp.util.getStartOfTheDay

// 🔹 Hourly Forecast
@Composable
fun HourlyForecastRow(cityName: String, homeViewModel: HomeViewModel) {
    val startOfDay = getStartOfTheDay()
    val hourlyForecast by homeViewModel.getHourlyForecast(cityName, startOfDay).collectAsState()

    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(hourlyForecast) { hourly ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(hourly.time, color =
                    Color(0xFF606060)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    painter = painterResource(id = mapWeatherIcon(hourly.description, hourly.icon)),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("${hourly.temp}°")
            }
        }
    }
}
