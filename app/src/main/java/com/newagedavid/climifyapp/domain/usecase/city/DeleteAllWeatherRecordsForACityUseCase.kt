package com.newagedavid.climifyapp.domain.usecase.city

import com.newagedavid.climifyapp.data.repository.CityRepository
import com.newagedavid.climifyapp.data.repository.DailyForecastRepository
import com.newagedavid.climifyapp.data.repository.HourlyCityForecastRepository


class DeleteAllWeatherRecordsForACityUseCase(
    private val repository: CityRepository,
    private val hourlyCityForecastRepository: HourlyCityForecastRepository,
    private val dailyForecastRepository: DailyForecastRepository,

) {
    suspend operator fun invoke(cityName: String) {
        try {
            // delete all records for this city
            repository.deleteCity(cityName)
            hourlyCityForecastRepository.deleteAllCityWeatherRecords(cityName)
            dailyForecastRepository.deleteAllCityWeatherRecords(cityName)

        } catch (e: Exception) {
            // Prevent saving city if fetch fails
            throw Exception("Failed to delete records: ${e.message}", e)
        }
    }
}

