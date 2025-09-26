package com.newagedavid.climifyapp.data.local.entity

import androidx.compose.ui.graphics.Color

data class WeatherBackgroundConfig(
    val imageRes: Int,   // drawable for the weather
    val textColor: Color // text color for overlay (black or white)
)
