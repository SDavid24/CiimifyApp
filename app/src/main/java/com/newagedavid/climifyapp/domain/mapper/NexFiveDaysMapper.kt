package com.newagedavid.climifyapp.domain.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.remote.model.ForecastResponse
import com.newagedavid.climifyapp.util.DateFormats
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.round

fun ForecastResponse.toNextFourDaysIncludingToday(): List<DailyCityForecast> {
    val savedAt = LocalDateTime.now().format(DateFormats.savedAtFormat)
    val today = LocalDate.now(ZoneId.systemDefault())

    return list
        .groupBy { forecast ->
            Instant.ofEpochSecond(forecast.dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        .filter { (date, _) ->
            !date.isBefore(today)  // only today and future
        }
        .map { (date, forecasts) ->
            val minTemp = forecasts.minOf { it.main.temp }
            val maxTemp = forecasts.maxOf { it.main.temp }

            val description = forecasts
                .flatMap { it.weather }
                .groupingBy { it.description }
                .eachCount()
                .maxByOrNull { it.value }
                ?.key ?: "unknown"

            val icon = forecasts.firstOrNull()?.weather?.firstOrNull()?.icon ?: "01d"

            val forecastAt = date.atStartOfDay().format(DateFormats.ForecastDateFormat)
            val label = date.format(DateFormats.ForcaseDateUILabelFormat)

            DailyCityForecast(
                id = 0,
                cityId = city.id.toString(),
                city = city.name,
                country = city.country,
                label = label,
                tempRange = "${round(minTemp).toInt()}°/${round(maxTemp).toInt()}°",
                description = description,
                icon = icon,
                forecastAt = forecastAt,
                savedAt = savedAt,
            )
        }
        .sortedBy { it.forecastAt }
        .take(5)  // today + next 4 days
}
