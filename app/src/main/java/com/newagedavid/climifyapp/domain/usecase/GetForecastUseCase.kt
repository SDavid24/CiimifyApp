package com.newagedavid.climifyapp.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.repository.WeatherRepository


class GetForecastUseCase(private val repository: WeatherRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(city: String): Result<List<DailyCityForecast>> =
        repository.getNextFourDays(city)
}

