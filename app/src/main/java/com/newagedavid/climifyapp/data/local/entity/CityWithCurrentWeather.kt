package com.newagedavid.climifyapp.data.local.entity

data class CityWithCurrentWeather(
    val id: Long,
    val name: String,
    val temp: String?,
    val description: String?,
    val icon: String?
)
