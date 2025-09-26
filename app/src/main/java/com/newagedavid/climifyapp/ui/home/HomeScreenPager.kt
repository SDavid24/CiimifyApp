package com.newagedavid.climifyapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.newagedavid.climifyapp.ui.city.CityManagerViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreenPager(
    navController: NavController,
    cityManagerViewModel: CityManagerViewModel = getViewModel()
) {
    val cityWeatherList by cityManagerViewModel.citiesWithWeather.collectAsState(initial = null)

    // If still loading, show nothing
    if (cityWeatherList == null) return

    // Once loaded, check if empty
    cityWeatherList?.let { list ->

        if (list.isEmpty()) {
            navController.navigate("cityManager") {
                popUpTo("home") { inclusive = true }
            }
            return@let
        }

        // Non-empty list → show pager
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { list.size }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                HomeScreen(navController, list[page])
            }

            // Pager Indicator - position at the bottom center
            PagerIndicator(
                pagerState = pagerState,
                pageCount = list.size,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // <- this is key
                    .padding(bottom = 16.dp)       // optional spacing from bottom
            )

        }


    }
}


@Composable
fun PagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(pageCount) { index ->
            val color = if (pagerState.currentPage == index) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .padding(4.dp)
                    .background(color, shape = CircleShape)
                    .zIndex(1f) // ensures it's drawn above pager content

            )
        }

        Spacer(modifier = Modifier.height(100.dp))

    }
}
