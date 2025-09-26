package com.newagedavid.climifyapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newagedavid.climifyapp.ui.city.mapWeatherIcon
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                    modifier = Modifier.size(27.dp).weight(1f)//.align(Alignment.CenterHorizontally)
                )
                val maxTemp = daily.tempRange.split("/").getOrNull(1)?.trim() ?: "--"
                Text(maxTemp,  fontSize = 19.sp, color = Color(0xFF505050), modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            }
        }
    }
}