package com.newagedavid.climifyapp.domain.usecase.city

import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import com.newagedavid.climifyapp.data.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetCitiesWithCurrentWeatherUseCase(
    private val repository: CityRepository,
) {
    operator fun invoke(): Flow<List<CityWithCurrentWeather>> =
        repository.getCitiesWithCurrentWeather().distinctUntilChanged()
}
