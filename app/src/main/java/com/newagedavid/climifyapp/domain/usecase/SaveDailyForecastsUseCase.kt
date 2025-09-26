package com.newagedavid.climifyapp.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.repository.DailyForecastRepository
import com.newagedavid.climifyapp.data.repository.WeatherRepository


class SaveDailyForecastsUseCase(private val repository: DailyForecastRepository) {

    /**
     * Saves a list of daily forecasts to the database.
     * Returns true if successful, or throws an exception if it fails.
     */
    suspend operator fun invoke(forecasts: List<DailyCityForecast>) {
        repository.saveForecasts(forecasts)
    }
}
