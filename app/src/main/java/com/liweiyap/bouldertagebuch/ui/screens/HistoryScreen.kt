package com.liweiyap.bouldertagebuch.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.liweiyap.bouldertagebuch.ui.MainViewModel

@Composable
fun HistoryScreen(
    viewModel: MainViewModel,
) {
    val todayRouteCount = viewModel.todayRouteCount.collectAsState().value

    Text(
        text = "${todayRouteCount.sum()}",
        style = MaterialTheme.typography.displayLarge,
        maxLines = 1,
        modifier = Modifier
            .fillMaxSize()
    )
}