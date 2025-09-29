package com.newagedavid.climifyapp.ui.city

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagedavid.climifyapp.data.local.entity.CityWithCurrentWeather
import com.newagedavid.climifyapp.domain.usecase.city.AddCityUseCase
import com.newagedavid.climifyapp.domain.usecase.city.DeleteAllWeatherRecordsForACityUseCase
import com.newagedavid.climifyapp.domain.usecase.city.GetCitiesWithCurrentWeatherUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CityManagerViewModel(
    private val addCityUseCase: AddCityUseCase,
    getCitiesWithCurrentWeatherUseCase: GetCitiesWithCurrentWeatherUseCase,
    private val deleteAllWeatherRecordsForACityUseCase: DeleteAllWeatherRecordsForACityUseCase
) : ViewModel() {


    val citiesWithWeather: StateFlow<List<CityWithCurrentWeather>?> =
        getCitiesWithCurrentWeatherUseCase()
            .stateIn(viewModelScope, SharingStarted.Lazily, null) // null = loading


    fun addCity(cityName: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                addCityUseCase(cityName)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    // 🔹 function to delete a city
    fun deleteCity(cityName: String, onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            try {
                deleteAllWeatherRecordsForACityUseCase(cityName)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

}
