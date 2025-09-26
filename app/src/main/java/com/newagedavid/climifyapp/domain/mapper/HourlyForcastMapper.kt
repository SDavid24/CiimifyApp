package com.newagedavid.climifyapp.domain.mapper

import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.data.remote.model.ForecastResponse
import com.newagedavid.climifyapp.util.DateFormats
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round


fun ForecastResponse.toTodayHourlyForecast(): List<HourlyCityForecast> {
    val today = LocalDate.now(ZoneId.systemDefault())
    val savedAt = LocalDateTime.now().format(DateFormats.savedAtFormat)

    return list
        .filter { forecast ->
            val dateTime = Instant.ofEpochSecond(forecast.dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            dateTime == today
        }
        .map { forecast ->
            val dateTime = Instant.ofEpochSecond(forecast.dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            // Format the LocalDateTime as a string
            val forecastAt = dateTime.format(DateFormats.ForecastDateFormat)

            HourlyCityForecast(
                id = 0,
                cityId = city.id.toString(),
                city = city.name,
                country = city.country,
                time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                temp =  round(forecast.main.temp).toInt().toString(),
                description = forecast.weather.firstOrNull()?.description ?: "unknown",
                icon = forecast.weather.firstOrNull()?.icon ?: "01d", // <-- map icon here
                forecastAt = forecastAt,
                savedAt = savedAt,
            )
        }
}
