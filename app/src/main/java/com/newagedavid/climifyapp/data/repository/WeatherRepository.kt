package com.newagedavid.climifyapp.data.repository


import android.util.Log
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.data.remote.apiservice.OpenWeatherApi
import com.newagedavid.climifyapp.domain.mapper.toNextFourDaysIncludingToday
import com.newagedavid.climifyapp.domain.mapper.toTodayHourlyForecast

class WeatherRepository(private val api: OpenWeatherApi) {

    suspend fun getTodayHourly(city: String): Result<List<HourlyCityForecast>> =
        runCatching {
            val forecastResponse = api.getFourDayForecast(city)
            forecastResponse.toTodayHourlyForecast()
        }.onFailure {
            Log.e("WeatherRepository", "Failed to fetch hourly forecast", it)
        }


    suspend fun getNextFourDays(city: String): Result<List<DailyCityForecast>> =
        runCatching {
            val forecastResponse = api.getFourDayForecast(city)
            forecastResponse.toNextFourDaysIncludingToday() // 👈 mapped to DailyCityForecast
        }.onFailure {
            Log.e("WeatherRepository", "Failed to fetch forecast", it)
        }


}
