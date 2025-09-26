package com.newagedavid.climifyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecasts(forecasts: List<HourlyCityForecast>)

    @Query("SELECT * FROM hourly_city_forecast WHERE city = :city ORDER BY time ASC")
    fun getForecastsForCity(city: String): Flow<List<HourlyCityForecast>>

    @Query("""
    SELECT * FROM hourly_city_forecast
    WHERE city = :cityName
      AND substr(forecastAt, 1, 10) = substr(:forecastDate, 1, 10)
    ORDER BY time ASC
""")
    fun getHourlyForecastForCity(cityName: String, forecastDate: String): Flow<List<HourlyCityForecast>>

}
