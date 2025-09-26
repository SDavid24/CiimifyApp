package com.newagedavid.climifyapp.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class RefreshCityWeatherUseCase(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val saveDailyForecastsUseCase: SaveDailyForecastsUseCase,
    private val saveHourlyCityForecastsUseCase: SaveHourlyForecastsUseCase
) {
    suspend operator fun invoke(city: String) {
        coroutineScope {
            // Hourly
            val hourlyJob = async {
                val res = getWeatherUseCase(city)
                res.fold(
                    onSuccess = { hourly ->
                        saveHourlyCityForecastsUseCase(hourly)
                    },
                    onFailure = { e ->
                        Timber.e(e, "Failed to fetch hourly")
                        throw e   // 🚨 stop execution
                    }
                )
            }

            // Daily
            val dailyJob = async {
                val res = getForecastUseCase(city)
                res.fold(
                    onSuccess = { daily ->
                        saveDailyForecastsUseCase(daily)
                    },
                    onFailure = { e ->
                        Timber.e(e, "Failed to fetch forecast")
                        throw e   // 🚨 stop execution
                    }
                )
            }

            // Wait for both
            hourlyJob.await()
            dailyJob.await()
        }
    }
}
