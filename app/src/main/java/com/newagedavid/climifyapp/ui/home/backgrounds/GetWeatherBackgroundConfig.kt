package com.newagedavid.climifyapp.ui.home.backgrounds

import androidx.compose.ui.graphics.Color
import com.newagedavid.climifyapp.R
import com.newagedavid.climifyapp.data.local.entity.WeatherBackgroundConfig

fun getWeatherBackgroundConfig(description: String, isNight: Boolean): WeatherBackgroundConfig {
    return when {
        description.contains("clear", ignoreCase = true) -> {
            if (isNight) WeatherBackgroundConfig(R.drawable.clear_night_sky, Color.White)
            else WeatherBackgroundConfig(R.drawable.sunnycloud, Color.Black)
        }
        description.contains("cloud", ignoreCase = true) -> {
            if (isNight) WeatherBackgroundConfig(R.drawable.cloudy_night, Color.White)
            else WeatherBackgroundConfig(R.drawable.cloudy_day, Color.Black)
        }
        description.contains("rain", ignoreCase = true) -> WeatherBackgroundConfig(R.drawable.rainy, Color.Black)
        description.contains("thunder", ignoreCase = true) -> WeatherBackgroundConfig(R.drawable.thunderstorm, Color.White)
        description.contains("snow", ignoreCase = true) -> WeatherBackgroundConfig(R.drawable.snowy_img, Color.Black)
        description.contains("fog", ignoreCase = true) -> WeatherBackgroundConfig(R.drawable.foggy_day, Color.Black)
        else -> {
            // fallback
            if (isNight) WeatherBackgroundConfig(R.drawable.clear_night_sky, Color.White)
            else WeatherBackgroundConfig(R.drawable.sunnycloud, Color.Black)
        }
    }
}
