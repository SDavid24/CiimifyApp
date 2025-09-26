package com.newagedavid.climifyapp.data.remote.apiservice

import com.newagedavid.climifyapp.BuildConfig
import com.newagedavid.climifyapp.data.remote.model.ForecastResponse
import com.newagedavid.climifyapp.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): WeatherResponse

    @GET("data/2.5/forecast")
    suspend fun getFourDayForecast(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): ForecastResponse
}
