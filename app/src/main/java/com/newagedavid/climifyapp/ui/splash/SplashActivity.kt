package com.newagedavid.climifyapp.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.newagedavid.climifyapp.R

@Composable
fun SplashScreen(navController: NavHostController) {
    // Automatically navigate after a delay
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000L) // 2 seconds delay
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Splash image
            Image(
                painter = painterResource(id = R.drawable.splash_img), // Replace with your image
                contentDescription = "Splash Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp) // adjust size
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Climify",
                color = Color.White,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


