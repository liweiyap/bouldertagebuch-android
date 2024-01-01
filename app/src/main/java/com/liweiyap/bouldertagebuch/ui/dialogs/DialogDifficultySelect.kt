package com.liweiyap.bouldertagebuch.ui.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.components.AppDialog
import com.liweiyap.bouldertagebuch.ui.theme.AppColor
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import java.util.Collections

@Composable
fun DialogDifficultySelect(
    onDismissRequest: () -> Unit,
    gym: Gym,
) {
    AppDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.title_dialog_difficulty_select),
        positiveButton = Pair(stringResource(id = R.string.button_dialog_positive_difficutly_select)) {},
    ) {
        val levels: ArrayList<ArrayList<Difficulty>> = remember(gym) {
            sortDifficultiesByLevel(gym)
        }

        val comparator: Comparator<ArrayList<Difficulty>> = remember(levels) {
            Comparator.comparing { level: ArrayList<Difficulty> ->
                level.size
            }
        }

        val maxDifficultiesPerLevel: Int = remember(levels, comparator) {
            Collections.max(levels, comparator).size
        }

        for (level in levels) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimensions.dialogDifficultySelectRouteMarginBottom),
            ) {
                Row(
                    modifier = Modifier.width(
                        max(0.dp, AppDimensions.dialogDifficultySelectRouteImageSize.times(maxDifficultiesPerLevel) + AppDimensions.dialogDifficultySelectRouteImageSpacing.times(maxDifficultiesPerLevel - 1))
                    ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = AppDimensions.dialogDifficultySelectRouteImageSpacing,
                        alignment = Alignment.CenterHorizontally,
                    ),
                ) {
                    for (difficulty in level) {
                        DifficultyColorIndicatorWithTooltip(difficulty = difficulty)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DifficultyColorIndicatorWithTooltip(
    difficulty: Difficulty,
) {
    if ((difficulty.level >= 0) && difficulty.grade.isBlank()) {
        DifficultyColorIndicator(
            difficulty = difficulty,
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
                modifier = Modifier.tooltipAnchor(),
            )
        }
    }
}

@Composable
private fun DifficultyColorIndicator(
    difficulty: Difficulty,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(AppDimensions.dialogDifficultySelectRouteImageSize)
            .clip(shape = CircleShape)
            .background(
                color = AppColor.translateRouteColorName(
                    difficulty.colorName,
                    isSystemInDarkTheme()
                )
            )
            .border(
                width = AppDimensions.dialogDifficultySelectRouteImageBorderWidth,
                color = AppColor.getBorderColorFromRouteColorName(
                    isSystemInDarkTheme()
                ),
                shape = CircleShape,
            )
    )
}

fun sortDifficultiesByLevel(gym: Gym): ArrayList<ArrayList<Difficulty>> {
    val levels: ArrayList<ArrayList<Difficulty>> = arrayListOf()

    for (difficulty in gym.difficulties) {
        if (levels.isEmpty()) {
            levels.add(arrayListOf(difficulty))
            continue
        }

        if (levels.last().last().level == difficulty.level) {
            levels.last().add(difficulty)
            continue
        }

        levels.add(arrayListOf(difficulty))
    }

    return levels
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DialogDifficultySelectPreview() {
    AppTheme {
        DialogDifficultySelect(
            onDismissRequest = {},
            gym = gymVels,
        )
    }
}