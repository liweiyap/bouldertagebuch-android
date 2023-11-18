package com.liweiyap.bouldertagebuch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var todayRouteCount by mutableIntStateOf(0)

    fun addToCount() {
        ++todayRouteCount
    }

    fun removeFromCount() {
        if (todayRouteCount > 0) {
            --todayRouteCount
        }
    }
}