package com.liweiyap.bouldertagebuch.ui.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.components.AppDialog
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.horizontalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.routes.sortDifficultiesByLevel
import com.liweiyap.bouldertagebuch.ui.theme.AppColor
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import com.liweiyap.bouldertagebuch.utils.vertical
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
        Row {
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

            val isFirstLevelNegative: Boolean = remember(levels) {
                levels[0].first().level < 0
            }

            var beginnerLevelOffsetY: Float by remember { mutableFloatStateOf(0F) }
            var expertLevelOffsetY: Float by remember { mutableFloatStateOf(0F) }

            val doShowDifficultyArrow: Boolean by remember {
                derivedStateOf {
                    (expertLevelOffsetY > 0.001F) && (expertLevelOffsetY - beginnerLevelOffsetY > 0.001F)
                }
            }

            if (doShowDifficultyArrow) {
                DifficultyArrow(beginnerLevelOffsetY, expertLevelOffsetY)
            }

            Column {
                for ((index, level) in levels.withIndex()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = (if (index == levels.size - 1)
                                    0.dp
                                else
                                    AppDimensions.dialogDifficultySelectRouteMarginBottom
                                )
                            )
                            .onGloballyPositioned { coordinates ->
                                if (canCalculateBeginnerLevelOffset(index, levels.size, isFirstLevelNegative)) {
                                    beginnerLevelOffsetY = coordinates.positionInParent().y
                                }
                                if (canCalculateExpertLevelOffset(index, levels.size, isFirstLevelNegative)) {
                                    expertLevelOffsetY = coordinates.positionInParent().y + coordinates.size.height
                                }
                            },
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

@Composable
private fun DifficultyArrow(
    beginnerLevelOffsetY: Float,
    expertLevelOffsetY: Float,
) {
    val beginnerLevelOffsetYDp = with(LocalDensity.current) { beginnerLevelOffsetY.toDp() }

    Text(
        text = stringResource(id = R.string.difficulty_arrow_label),
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        modifier = Modifier
            .vertical()
            .rotate(-90F)
            .offset(x = -beginnerLevelOffsetYDp)
            .horizontalScrollWithScrollbar(
                state = rememberScrollState(),
                scrollbarConfig = ScrollBarConfig(
                    doFade = false,
                )
            ),
    )

    val arrowColor: Color = AppColor.getBorderColorFromRouteColorName(
        isSystemInDarkTheme()
    )

    Canvas(
        modifier = Modifier.width(AppDimensions.dialogDifficultyArrowLayoutWidth)
    ) {
        drawLine(
            start = Offset(
                x = AppDimensions.dialogDifficultyArrowLayoutWidth.times(0.5F).toPx(),
                y = beginnerLevelOffsetY + 2.dp.toPx(),
            ),
            end = Offset(
                x = AppDimensions.dialogDifficultyArrowLayoutWidth.times(0.5F).toPx(),
                y = expertLevelOffsetY - AppDimensions.dialogDifficultySelectRouteImageSize.times(0.25F).toPx(),
            ),
            color = arrowColor,
            strokeWidth = AppDimensions.dialogDifficultyArrowStrokeWidth,
            cap = StrokeCap.Round,
        )

        // https://stackoverflow.com/a/69752246/12367873
        val trianglePath = Path().apply {
            moveTo(
                x = AppDimensions.dialogDifficultyArrowLayoutWidth.times(0.5F).toPx(),
                y = expertLevelOffsetY,
            )
            lineTo(
                x = AppDimensions.dialogDifficultyArrowLayoutWidth.times(0.25F).toPx(),
                y = expertLevelOffsetY - AppDimensions.dialogDifficultySelectRouteImageSize.times(0.5F).toPx()
            )
            lineTo(
                x = AppDimensions.dialogDifficultyArrowLayoutWidth.times(0.75F).toPx(),
                y = expertLevelOffsetY - AppDimensions.dialogDifficultySelectRouteImageSize.times(0.5F).toPx()
            )
            close()
        }

        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(trianglePath),
                paint = Paint().apply {
                    color = arrowColor
                    pathEffect = PathEffect.cornerPathEffect(radius = AppDimensions.dialogDifficultyArrowStrokeWidth)
                }
            )
        }
    }
}

fun canCalculateLevelOffsets(
    index: Int,
    levelCount: Int,
    isFirstLevelNegative: Boolean,
): Boolean {
    if (index >= levelCount) return false
    if (levelCount < 2) return false
    return !(levelCount < 3 && isFirstLevelNegative)
}

fun canCalculateBeginnerLevelOffset(
    index: Int,
    levelCount: Int,
    isFirstLevelNegative: Boolean,
): Boolean {
    if (!canCalculateLevelOffsets(index, levelCount, isFirstLevelNegative)) return false
    if (isFirstLevelNegative) return (index == 1)
    return (index == 0)
}

fun canCalculateExpertLevelOffset(
    index: Int,
    levelCount: Int,
    isFirstLevelNegative: Boolean,
): Boolean {
    if (!canCalculateLevelOffsets(index, levelCount, isFirstLevelNegative)) return false
    return (index == levelCount - 1)
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