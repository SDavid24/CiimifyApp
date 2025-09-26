package com.newagedavid.climifyapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                HomeScreen(navController, list[page])
            }

            PagerIndicator(pagerState = pagerState, pageCount = list.size)
        }
    }
}



@Composable
fun PagerIndicator(pagerState: PagerState, pageCount: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(pageCount) { index ->
            val color = if (pagerState.currentPage == index) Color.White else Color.Gray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .padding(4.dp)
                    .background(color, shape = CircleShape)
            )
        }
    }
}
