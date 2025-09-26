package com.newagedavid.climifyapp.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.newagedavid.climifyapp.R

@Composable
fun SplashScreen(navController: NavHostController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_weather))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1, // play once
        speed = 1.2f
    )

    // Navigate when animation finishes
    LaunchedEffect(progress) {
        if (progress == 1f) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column (modifier = Modifier.align(Alignment.Center)) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
            )

            Text("Climify", style = TextStyle(
                color = Color.White,
                fontSize = 34.sp,
                textAlign = TextAlign.Center
            ))

        }

    }
}

