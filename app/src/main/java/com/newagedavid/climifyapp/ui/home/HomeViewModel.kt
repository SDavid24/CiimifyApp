package com.newagedavid.climifyapp.ui.home


import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagedavid.climifyapp.data.local.entity.DailyCityForecast
import com.newagedavid.climifyapp.data.local.entity.HourlyCityForecast
import com.newagedavid.climifyapp.domain.usecase.GetHourlyForecastUseCase
import com.newagedavid.climifyapp.domain.usecase.GetNextFourDaysUseCase
import com.newagedavid.climifyapp.domain.usecase.RefreshCityWeatherUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val refreshCityWeatherUseCase: RefreshCityWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getNextFourDaysUseCase: GetNextFourDaysUseCase,
    private val prefs: SharedPreferences
) : ViewModel() {

    val cityState = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val errorState = mutableStateOf<String?>(null)
    val favoriteSaved = mutableStateOf(false)

    init {
        val favorite = prefs.getString("favorite_city", "") ?: ""
        cityState.value = favorite
    }

    fun getHourlyForecast(cityName: String, forecastAt: String): StateFlow<List<HourlyCityForecast>> {
        return getHourlyForecastUseCase(cityName, forecastAt)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun nextFourDays(cityName: String, startOfToday: String): StateFlow<List<DailyCityForecast>> {
        return getNextFourDaysUseCase(cityName, startOfToday)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }


    fun fetchWeather() {
        val city = cityState.value.trim()
        if (city.isBlank()) {
            errorState.value = "Please enter a city"
            return
        }

        isLoading.value = true
        errorState.value = null

        viewModelScope.launch {
            try {
                refreshCityWeatherUseCase(city)  // <--- fetch + save hourly & forecast
                isLoading.value = false
            } catch (e: Exception) {
                errorState.value = e.message ?: "Unknown error"
                isLoading.value = false
            }
        }
    }

    fun saveFavorite() {
        prefs.edit().putString("favorite_city", cityState.value.trim()).apply()
        favoriteSaved.value = true
    }
}

