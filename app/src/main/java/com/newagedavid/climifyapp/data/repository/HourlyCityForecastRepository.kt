package com.newagedavid.climifyapp.data.repository

import com.newagedavid.climifyapp.data.local.dao.HourlyForecastDao
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import kotlinx.coroutines.flow.Flow

class HourlyCityForecastRepository(private val dao: HourlyForecastDao) {

    suspend fun saveForecasts(forecasts: List<HourlyCityForecast>) =
        dao.insertHourlyForecasts(forecasts)

    fun getHourlyForecast(cityName: String, forecastAt: String): Flow<List<HourlyCityForecast>> {
        return dao.getHourlyForecastForCity(cityName, forecastAt)
    }

    suspend fun deleteAllCityWeatherRecords(cityName: String){
        dao.deleteAllHourlyCityWeatherRecords(cityName)
    }
}