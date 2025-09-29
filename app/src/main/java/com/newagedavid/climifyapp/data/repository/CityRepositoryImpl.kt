package com.newagedavid.climifyapp.data.repository

import com.newagedavid.climifyapp.data.local.dao.CityDao
import com.newagedavid.climifyapp.data.local.dao.HourlyForecastDao
import com.newagedavid.climifyapp.data.local.entity.City
import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import com.newagedavid.climifyapp.util.getCurrentFormattedTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val hourlyForecastDao: HourlyForecastDao,
) : CityRepository {

    override suspend fun addCity(cityName: String): City {
        val existing = cityDao.getByName(cityName)
        if (existing != null) return existing

        val newCity = City(0, name = cityName, getCurrentFormattedTime())
        val id = cityDao.insertCity(newCity)

        return if (id > 0) newCity.copy(id = id) else cityDao.getByName(cityName)!!
    }

    override suspend fun deleteCity(cityName: String) {
        cityDao.deleteCity(cityName)
    }

    override suspend fun getCityByName(cityName: String): City? =
        cityDao.getByName(cityName)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCitiesWithCurrentWeather(): Flow<List<CityWithCurrentWeather>> =
        cityDao.getAllCities().flatMapLatest { cities ->
            if (cities.isEmpty()) return@flatMapLatest flowOf(emptyList())

            combine(cities.map { city ->
                hourlyForecastDao.getForecastsForCity(city.name.toString())
                    .map { forecasts ->
                        val now = LocalTime.now()
                        val closest = forecasts.minByOrNull { forecast ->
                            val forecastTime = LocalTime.parse(
                                forecast.time,
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                            kotlin.math.abs(Duration.between(now, forecastTime).toMinutes())
                        }

                        CityWithCurrentWeather(
                            id = city.id,
                            name = city.name,
                            temp = closest?.temp,
                            description = closest?.description,
                            icon = closest?.description
                        )
                    }
            }) { it.toList() }
        }

}
