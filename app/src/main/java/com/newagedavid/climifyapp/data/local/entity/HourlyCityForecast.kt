package com.newagedavid.climifyapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hourly_city_forecast",
    indices = [Index(value = ["cityId", "time"], unique = true)]
)
data class HourlyCityForecast(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityId: String = "",
    val city: String = "",
    val country: String = "",
    val temp: String = "",
    val time: String = "",
    val description: String = "",
    val icon: String = "", // <-- store icon code from API
    val forecastAt: String = "",
    val savedAt: String = ""
)

