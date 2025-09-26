package com.newagedavid.climifyapp.domain.usecase

import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.data.repository.HourlyCityForecastRepository
import kotlinx.coroutines.flow.Flow

class GetHourlyForecastUseCase(
    private val repository: HourlyCityForecastRepository
) {
    operator fun invoke(cityName: String, forecastAt: String): Flow<List<HourlyCityForecast>> {
        return repository.getHourlyForecast(cityName, forecastAt)
    }
}
