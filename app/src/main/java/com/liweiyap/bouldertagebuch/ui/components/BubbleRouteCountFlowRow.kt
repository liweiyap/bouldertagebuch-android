package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BubbleRouteCountFlowRow(
    gym: Gym,
    routeCount: List<Int>,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.bubbleRouteCountFlowSpacingHorizontal),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.bubbleRouteCountFlowSpacingVertical),
    ) {
        val levels: ArrayList<ArrayList<Difficulty>> = remember(gym) {
            gym.getDifficultiesSortedByLevel()
        }

        for ((index, level) in levels.withIndex()) {
            BubbleRouteCountFlowRowItem(
                index = index,
                level = level,
                routeCount = routeCount,
            )
        }
    }
}

@Composable
private fun BubbleRouteCountFlowRowItem(
    index: Int,
    level: ArrayList<Difficulty>,
    routeCount: List<Int>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.bubbleRouteCountFlowItemSpacing),
        ) {
            for (difficulty in level) {
                DifficultyColorIndicator(
                    difficulty = difficulty,
                    size = AppDimensions.bubbleRouteCountFlowItemSize,
                )
            }
        }

        Text(
            text = routeCount[index].toString(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
    }
}