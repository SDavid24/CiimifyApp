package com.newagedavid.climifyapp.data.repository

import com.newagedavid.climifyapp.data.local.entity.City
import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import kotlinx.coroutines.flow.Flow

// data/repository/CityRepository.kt


interface CityRepository {
    suspend fun addCity(cityName: String): City
    suspend fun deleteCity(cityName: String)
    suspend fun getCityByName(cityName: String): City?
    fun getCitiesWithCurrentWeather(): Flow<List<CityWithCurrentWeather>>
}
