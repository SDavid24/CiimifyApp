package com.newagedavid.climifyapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @field:Json(name = "city") val city: City,
    @field:Json(name = "list") val list: List<ForecastItem>
)

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "country") val country: String
)

@JsonClass(generateAdapter = true)
data class ForecastItem(
    @field:Json(name = "dt") val dt: Long,
    @field:Json(name = "main") val main: Main,
    @field:Json(name = "weather") val weather: List<WeatherDescription>
)

