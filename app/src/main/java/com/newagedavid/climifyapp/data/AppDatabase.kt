package com.newagedavid.climifyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newagedavid.climifyapp.data.local.dao.CityDao
import com.newagedavid.climifyapp.data.local.dao.DailyCityForecastDao
import com.newagedavid.climifyapp.data.local.dao.HourlyForecastDao
import com.newagedavid.climifyapp.data.local.entity.City
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast

@Database(entities =
    [
    DailyCityForecast::class,
    HourlyCityForecast::class,
    City::class,
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyCityForecastDao(): DailyCityForecastDao
    abstract fun hourlyForecastDao(): HourlyForecastDao
    abstract fun cityDao(): CityDao
}
