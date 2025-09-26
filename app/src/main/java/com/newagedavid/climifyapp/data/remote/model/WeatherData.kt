package com.newagedavid.climifyapp.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData(
    val city: String,
    val temp: Double,
    val description: String,
    val icon: String,
    val min: Double,
    val max: Double,
    val humidity: Int,
    val condition: String
) : Parcelable