package com.liweiyap.bouldertagebuch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.screens.GymCreateScreen
import com.liweiyap.bouldertagebuch.ui.screens.HistoryScreen
import com.liweiyap.bouldertagebuch.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val appNavHostController = rememberAppNavHostController()

    NavHost(
        navController = appNavHostController.navController,
        startDestination = NavDestinationScreen.Home.route,
    ) {
        composable(route = NavDestinationScreen.Home.route) {
            HomeScreen(appNavHostController, hiltViewModel<MainViewModel>())
        }

        composable(route = NavDestinationScreen.History.route) {
            HistoryScreen(hiltViewModel<MainViewModel>())
        }

        composable(route = NavDestinationScreen.GymCreate.route) {
            GymCreateScreen(hiltViewModel<MainViewModel>())
        }
    }
}

@Composable
fun rememberAppNavHostController(
    navController: NavHostController = rememberNavController()
): AppNavHostController = remember(navController) {
    AppNavHostController(navController)
}

@Stable
class AppNavHostController(
    val navController: NavHostController,
) {
    fun navigateToHistory() {
        navController.navigate(route = NavDestinationScreen.History.route) {
            launchSingleTop = true
        }
    }

    fun navigateToGymCreate() {
        navController.navigate(route = NavDestinationScreen.GymCreate.route) {
            launchSingleTop = true
        }
    }
}