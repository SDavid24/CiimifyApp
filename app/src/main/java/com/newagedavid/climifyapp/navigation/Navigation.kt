package com.example.nava.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.newagedavid.climifyapp.ui.city.AddCityScreen
import com.newagedavid.climifyapp.ui.city.CityManagerScreen
import com.newagedavid.climifyapp.ui.home.HomeScreenPager
import com.newagedavid.climifyapp.ui.splash.SplashScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        //splash screen
        composable("splash") { SplashScreen(navController) }

        // Home screen with pager
        composable("home") {
            HomeScreenPager(navController)
        }


        // City manager
        composable("cityManager") { CityManagerScreen(navController) }

        // Add city
        composable("addCity") { AddCityScreen(navController) }
    }
}
