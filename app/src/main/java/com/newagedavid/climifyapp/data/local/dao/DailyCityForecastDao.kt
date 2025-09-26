package com.newagedavid.climifyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import kotlinx.coroutines.flow.Flow


@Dao
interface DailyCityForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityForecast(dailyForecast: List<DailyCityForecast>)

    @Query("""
        SELECT * FROM daily_city_forecast 
        WHERE city = :cityName 
          AND forecastAt >= :startOfToday 
        ORDER BY forecastAt ASC 
        LIMIT 4
    """)
    fun getNextFourDays(cityName: String, startOfToday: String): Flow<List<DailyCityForecast>>


    //@Query("SELECT * FROM city_daily_forecast Where city ORDER BY  DESC")
   // fun getNextFourDiaCityForecast(): Flow<List<DailyCityForecast>>

    @Query("DELETE FROM daily_city_forecast")
    suspend fun clearRideHistory()
}
