package com.newagedavid.climifyapp.ui.home.backgrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.newagedavid.climifyapp.R

@Composable
fun WeatherBackgroundRainy() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A6572), // Cloudy gray-blue
                        Color(0xFFE0E0E0)  // White bottom
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.rainy), // realistic rainy clouds
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Image(
            painter = painterResource(id = R.drawable.rainy), // semi-transparent PNG
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            alpha = 0.3f, // subtle
            modifier = Modifier.fillMaxSize()
        )
    }
}
