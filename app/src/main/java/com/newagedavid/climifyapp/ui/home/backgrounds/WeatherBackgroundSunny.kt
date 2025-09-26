package com.newagedavid.climifyapp.ui.home.backgrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.newagedavid.climifyapp.R


@Composable
fun SetWeatherBackgroundConfig(description: String, isNight: Boolean) {
    val config = getWeatherBackgroundConfig(description, isNight)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // default background
    ) {
        // Weather image (upper half)
        Image(
            painter = painterResource(id = config.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
        )

        // Fade gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 1f)
                        )
                    )
                )
        )

        // Upper-half overlay for texts
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
        ) {
            CompositionLocalProvider(LocalContentColor provides config.textColor) {
                // All texts in the upper half automatically take this color
            }
        }
    }
}


@Composable
fun WeatherBackgroundSunny() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // overall background
    ) {
        // Image at the top
        Image(
            painter = painterResource(id = R.drawable.thunderstorm),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
        )

        // Gradient overlay to fade image into white
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,       // top: show image
                            Color.White.copy(alpha = 1f) // bottom: fade into background
                        )
                    )
                )
        )
    }
}

