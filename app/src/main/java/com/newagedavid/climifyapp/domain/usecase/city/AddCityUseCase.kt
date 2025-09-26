package com.newagedavid.climifyapp.domain.usecase.city

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.local.entity.City
import com.newagedavid.climifyapp.data.repository.CityRepository
import com.newagedavid.climifyapp.domain.usecase.RefreshCityWeatherUseCase


@RequiresApi(Build.VERSION_CODES.O)
class AddCityUseCase(
    private val repository: CityRepository,
    private val refreshCityWeatherUseCase: RefreshCityWeatherUseCase
) {
    suspend operator fun invoke(cityName: String): City {
        try {
            // Fetch weather first (this now throws if failed)
            refreshCityWeatherUseCase(cityName)

            // ✅ Only add city if fetch succeeded fully
            return repository.addCity(cityName)

        } catch (e: Exception) {
            // Prevent saving city if fetch fails
            throw Exception("Failed to fetch weather: ${e.message}", e)
        }
    }
}

