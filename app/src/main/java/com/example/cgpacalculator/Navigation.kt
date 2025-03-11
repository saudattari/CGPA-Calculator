package com.example.cgpacalculator

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cgpacalculator.Screens.CalculateCgpa
import com.example.cgpacalculator.Screens.MainScreen
import com.example.cgpacalculator.Screens.SplashScreen

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "Splash") {
        composable("Splash") {
            SplashScreen(navController = navHostController)
        }
        composable(route = "CalculateCgpa/{batch}", arguments = listOf(navArgument("batch") { type = NavType.StringType })
        ) { backStackEntry ->
            val batch = backStackEntry.arguments?.getString("batch") ?: "Unknown"
            CalculateCgpa(batch = batch, navController = navHostController)
        }
        composable("Main") {
            MainScreen(navController = navHostController)
        }
    }
}