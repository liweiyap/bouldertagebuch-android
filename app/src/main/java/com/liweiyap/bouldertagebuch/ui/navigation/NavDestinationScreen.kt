package com.liweiyap.bouldertagebuch.ui.navigation

sealed class NavDestinationScreen(val route: String) {
    data object Home: NavDestinationScreen("HOME")
    data object History: NavDestinationScreen("HISTORY")
    data object GymCreate: NavDestinationScreen("GYM_CREATE")
}