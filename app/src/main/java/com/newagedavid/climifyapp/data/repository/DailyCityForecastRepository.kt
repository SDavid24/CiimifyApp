package com.newagedavid.climifyapp.data.repository

import com.newagedavid.climifyapp.data.local.dao.DailyCityForecastDao
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import kotlinx.coroutines.flow.Flow

class DailyForecastRepository(private val dao: DailyCityForecastDao) {
    suspend fun saveForecasts(forecasts: List<DailyCityForecast>) =
        dao.insertCityForecast(forecasts)

    fun getNextFourDays(cityName: String, startOfToday: String): Flow<List<DailyCityForecast>> {
        return dao.getNextFourDays(cityName, startOfToday)
    }

    suspend fun deleteAllCityWeatherRecords(cityName: String){
        dao.deleteAllDailyCityWeatherRecords(cityName)
    }

}
