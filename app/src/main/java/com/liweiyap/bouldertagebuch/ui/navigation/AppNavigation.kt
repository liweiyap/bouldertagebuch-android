package com.liweiyap.bouldertagebuch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.screens.HistoryScreen
import com.liweiyap.bouldertagebuch.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationScreen.Home.route,
    ) {
        composable(route = NavDestinationScreen.Home.route) {
            HomeScreen(navController, hiltViewModel<MainViewModel>())
        }

        composable(route = NavDestinationScreen.History.route) {
            HistoryScreen(hiltViewModel<MainViewModel>())
        }
    }
}