package com.newagedavid.climifyapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import com.newagedavid.climifyapp.ui.home.backgrounds.WeatherBackground
import org.koin.androidx.compose.getViewModel


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
                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(90.dp))

                // Temperature
                Text(
                    text = cityWeather.temp?.let { "$it°" } ?: "--",
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

                Spacer(modifier = Modifier.height(42.dp))

                // ---------------- Lower-half content ----------------

                Box(
                    modifier = Modifier
                        //.fillMaxSize()
                      //  .padding(16.dp)
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart).fillMaxSize() // ✅ works because parent is Box
                    ) {
                        // Hourly forecast row
                        HourlyForecastRow(cityName = cityWeather.name, homeViewModel)

                        Spacer(modifier = Modifier.height(45.dp))

                        // Daily forecast list
                        DailyForecastList(cityName = cityWeather.name, homeViewModel)
                    }
                }


        }


    }}
}







