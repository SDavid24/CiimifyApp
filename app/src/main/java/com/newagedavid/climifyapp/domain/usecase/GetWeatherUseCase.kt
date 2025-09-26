package com.newagedavid.climifyapp.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.data.repository.WeatherRepository


class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Result<List<HourlyCityForecast>> =
        repository.getTodayHourly(city)
}
