package com.newagedavid.climifyapp.ui.home

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import com.newagedavid.climifyapp.ui.city.mapWeatherIcon
import com.newagedavid.climifyapp.ui.home.backgrounds.WeatherBackground
import com.newagedavid.climifyapp.util.getStartOfTheDay
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(
    navController: NavController,
    cityWeather: CityWithCurrentWeather,
    homeViewModel: HomeViewModel = getViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val isNight = cityWeather.icon?.endsWith("n") == true

        // WeatherBackground determines the image and the text/icon color for the upper-half
        WeatherBackground(description = cityWeather.description ?: "Sunny", isNight = isNight) {
       // WeatherBackground(description = "clear" ?: "Sunny", isNight = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // ---------------- Top Bar ----------------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // City name inherits text color from background
                    Text(
                        text = cityWeather.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )

                    // Add icon now inherits color automatically
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add City",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                navController.navigate("cityManager") {
                                    popUpTo("home") { inclusive = false }
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // Temperature
                Text(
                    text = "${cityWeather.temp ?: "--"}°",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Weather Description
                Text(
                    text = cityWeather.description ?: "--",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

            }
        }

        Spacer(modifier = Modifier.height(250.dp)) // Push down to avoid overlapping upper-half

        // ---------------- Lower-half content ----------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Spacer(modifier = Modifier.height(250.dp)) // Push down to avoid overlapping upper-half

            // Hourly forecast row (default colors)
            HourlyForecastRow(cityName = cityWeather.name, homeViewModel)

            Spacer(modifier = Modifier.height(32.dp))

            // Daily forecast list (default colors)
            DailyForecastList(cityName = cityWeather.name, homeViewModel)
        }
    }
}


// 🔹 Hourly Forecast
@Composable
fun HourlyForecastRow(cityName: String, homeViewModel: HomeViewModel) {
    val startOfDay = getStartOfTheDay()
    val hourlyForecast by homeViewModel.getHourlyForecast(cityName, startOfDay).collectAsState()

    Log.d("HourlyForecastRow",  "homeViewModel.getHourlyForecast.collectAsState()  $cityName & $startOfDay" )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(hourlyForecast) { hourly ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(hourly.time, color =
                    Color(0xFF606060))
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


@Composable
fun DailyForecastList(cityName: String, vm: HomeViewModel) {
    val startOfToday = LocalDate.now().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val dailyForecasts by vm.nextFourDays(cityName, startOfToday).collectAsState()

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(dailyForecasts) { daily ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(daily.label, fontSize = 19.sp, color = Color(0xFF505050), modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = mapWeatherIcon(daily.description, daily.icon)),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )
                val maxTemp = daily.tempRange.split("/").getOrNull(1)?.trim() ?: "--"
                Text(maxTemp,  fontSize = 19.sp, color = Color(0xFF505050), modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            }
        }
    }
}


