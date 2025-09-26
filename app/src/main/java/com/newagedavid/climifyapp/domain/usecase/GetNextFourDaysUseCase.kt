package com.newagedavid.climifyapp.domain.usecase

import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.repository.DailyForecastRepository
import kotlinx.coroutines.flow.Flow

class GetNextFourDaysUseCase(private val repository: DailyForecastRepository) {

    operator fun invoke(cityName: String, startOfToday: String): Flow<List<DailyCityForecast>> {
        return repository.getNextFourDays(cityName, startOfToday)
    }
}
