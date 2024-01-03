package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.ui.theme.AppColor
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyColorIndicatorWithTooltip(
    difficulty: Difficulty,
    size: Dp,
) {
    if ((difficulty.level >= 0) && difficulty.grade.isBlank()) {
        DifficultyColorIndicator(
            difficulty = difficulty,
            size = size,
        )
    }
    else {
        PlainTooltipBox(
            tooltip = {
                Text(
                    if (difficulty.level == -1)
                        stringResource(id = R.string.difficulty_special_event_tooltip)
                    else
                        stringResource(id = R.string.difficulty_tooltip_prefix) + difficulty.grade
                )
            },
        ) {
            DifficultyColorIndicator(
                difficulty = difficulty,
                size = size,
                modifier = Modifier.tooltipAnchor(),
            )
        }
    }
}

@Composable
fun DifficultyColorIndicator(
    difficulty: Difficulty,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape = CircleShape)
            .background(
                color = AppColor.translateRouteColorName(
                    difficulty.colorName,
                    isSystemInDarkTheme()
                )
            )
            .border(
                width = AppDimensions.difficultyColorIndicatorBorderWidth,
                color = AppColor.getBorderColorFromRouteColorName(
                    isSystemInDarkTheme()
                ),
                shape = CircleShape,
            )
    )
}