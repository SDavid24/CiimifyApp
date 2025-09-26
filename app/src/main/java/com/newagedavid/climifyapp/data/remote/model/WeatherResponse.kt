package com.newagedavid.climifyapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "weather") val weather: List<WeatherDescription>,
    @field:Json(name = "main") val main: Main
)

data class WeatherDescription(
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String? // e.g. "10d"

)

data class Main(
    @field:Json(name = "temp") val temp: Double
)
