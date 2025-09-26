package com.newagedavid.climifyapp.domain.usecase

import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.data.repository.HourlyCityForecastRepository


class SaveHourlyForecastsUseCase(private val repository: HourlyCityForecastRepository) {

    /**
     * Saves a list of hourly forecasts to the database.
     * Returns true if successful, or throws an exception if it fails.
     */
    suspend operator fun invoke(forecasts: List<HourlyCityForecast>) {
        repository.saveForecasts(forecasts)
    }

}

