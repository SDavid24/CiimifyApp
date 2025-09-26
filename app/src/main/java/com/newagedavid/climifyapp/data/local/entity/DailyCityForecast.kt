package com.newagedavid.climifyapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_city_forecast",
    indices = [Index(value = ["cityId", "forecastAt"], unique = true)]
)
data class DailyCityForecast(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityId: String,
    val city: String,
    val country: String,
    val label: String,
    val tempRange: String,
    val description: String,
    val icon: String = "", // <-- store icon code from API
    val forecastAt: String,
    val savedAt: String
)
