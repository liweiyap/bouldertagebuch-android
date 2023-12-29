package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.lang.Float.max

private val defaultScrollbarIndicatorThickness: Dp = 4.dp
private val defaultScrollbarIndicatorColor: Color = Color.LightGray
private val defaultScrollbarPadding: PaddingValues = PaddingValues(all = 0.dp)

private fun defaultScrollbarAlpha(state: ScrollState): Float = if (state.isScrollInProgress) 0.8F else 0F
private fun defaultScrollbarAlphaAnimationSpec(state: ScrollState): AnimationSpec<Float> = tween(
    delayMillis = if (state.isScrollInProgress) 0 else 1500,
    durationMillis = if (state.isScrollInProgress) 150 else 500
)

/**
 * https://stackoverflow.com/a/76630645/12367873
 * Supports both LTR and RTL layout directions
 */
fun Modifier.scrollbar(
    state: ScrollState,
    direction: Orientation,
    indicatorThickness: Dp = defaultScrollbarIndicatorThickness,
    indicatorColor: Color = defaultScrollbarIndicatorColor,
    doFade: Boolean = true,  // <- added by LWY
    alpha: Float = defaultScrollbarAlpha(state),
    alphaAnimationSpec: AnimationSpec<Float> = defaultScrollbarAlphaAnimationSpec(state),
    padding: PaddingValues = defaultScrollbarPadding
): Modifier = composed {
    val scrollbarAlpha: Float = if (doFade) animateFloatAsState(
        targetValue = alpha,
        animationSpec = alphaAnimationSpec,
        label = "scrollbarAlpha",
    ).value else 1F

    drawWithContent {
        drawContent()

        val canShowScrollBar: Boolean = (!doFade) || (state.isScrollInProgress || scrollbarAlpha > 0.0F)

        // Draw scrollbar only if currently scrolling or if scroll animation is ongoing.
        if (canShowScrollBar) {
            val (topPadding, bottomPadding, startPadding, endPadding) = listOf(
                padding.calculateTopPadding().toPx(), padding.calculateBottomPadding().toPx(),
                padding.calculateStartPadding(layoutDirection).toPx(),
                padding.calculateEndPadding(layoutDirection).toPx()
            )
            val contentOffset: Int = state.value
            val viewPortLength: Float = if (direction == Orientation.Vertical) size.height else size.width
            val viewPortCrossAxisLength: Float = if (direction == Orientation.Vertical) size.width else size.height
            val contentLength: Float = max(viewPortLength + state.maxValue, 0.001F)  // To prevent divide by zero error
            val indicatorLength: Float = ((viewPortLength / contentLength) * viewPortLength) - (
                if (direction == Orientation.Vertical) topPadding + bottomPadding
                else startPadding + endPadding
            )

            // added by LWY
            val doShowScrollbar: Boolean = (indicatorLength >= viewPortLength)
            if (doShowScrollbar) return@drawWithContent

            val indicatorThicknessPx: Float = indicatorThickness.toPx()

            val scrollOffsetViewPort: Float = viewPortLength * contentOffset / contentLength

            val scrollbarSizeWithoutInsets: Size = if (direction == Orientation.Vertical)
                Size(indicatorThicknessPx, indicatorLength)
            else Size(indicatorLength, indicatorThicknessPx)

            val scrollbarPositionWithoutInsets: Offset = if (direction == Orientation.Vertical)
                Offset(
                    x = if (layoutDirection == LayoutDirection.Ltr)
                        viewPortCrossAxisLength - indicatorThicknessPx - endPadding
                    else startPadding,
                    y = scrollOffsetViewPort + topPadding
                )
            else
                Offset(
                    x = if (layoutDirection == LayoutDirection.Ltr)
                        scrollOffsetViewPort + startPadding
                    else viewPortLength - scrollOffsetViewPort - indicatorLength - endPadding,
                    y = viewPortCrossAxisLength - indicatorThicknessPx - bottomPadding
                )

            drawRoundRect(
                color = indicatorColor,
                cornerRadius = CornerRadius(
                    x = indicatorThicknessPx / 2, y = indicatorThicknessPx / 2
                ),
                topLeft = scrollbarPositionWithoutInsets,
                size = scrollbarSizeWithoutInsets,
                alpha = scrollbarAlpha
            )
        }
    }
}

data class ScrollBarConfig(
    val indicatorThickness: Dp = defaultScrollbarIndicatorThickness,
    val indicatorColor: Color = defaultScrollbarIndicatorColor,
    val doFade: Boolean = true,
    val alpha: Float? = null,
    val alphaAnimationSpec: AnimationSpec<Float>? = null,
    val padding: PaddingValues = defaultScrollbarPadding
)

fun Modifier.verticalScrollWithScrollbar(
    state: ScrollState,
    enabled: Boolean = true,
    flingBehavior: FlingBehavior? = null,
    reverseScrolling: Boolean = false,
    scrollbarConfig: ScrollBarConfig = ScrollBarConfig()
) = this
    .scrollbar(
        state, Orientation.Vertical,
        indicatorThickness = scrollbarConfig.indicatorThickness,
        indicatorColor = scrollbarConfig.indicatorColor,
        doFade = scrollbarConfig.doFade,
        alpha = scrollbarConfig.alpha ?: defaultScrollbarAlpha(state),
        alphaAnimationSpec = scrollbarConfig.alphaAnimationSpec ?: defaultScrollbarAlphaAnimationSpec(state),
        padding = scrollbarConfig.padding
    )
    .verticalScroll(state, enabled, flingBehavior, reverseScrolling)

fun Modifier.horizontalScrollWithScrollbar(
    state: ScrollState,
    enabled: Boolean = true,
    flingBehavior: FlingBehavior? = null,
    reverseScrolling: Boolean = false,
    scrollbarConfig: ScrollBarConfig = ScrollBarConfig()
) = this
    .scrollbar(
        state, Orientation.Horizontal,
        indicatorThickness = scrollbarConfig.indicatorThickness,
        indicatorColor = scrollbarConfig.indicatorColor,
        doFade = scrollbarConfig.doFade,
        alpha = scrollbarConfig.alpha ?: defaultScrollbarAlpha(state),
        alphaAnimationSpec = scrollbarConfig.alphaAnimationSpec ?: defaultScrollbarAlphaAnimationSpec(state),
        padding = scrollbarConfig.padding
    )
    .horizontalScroll(state, enabled, flingBehavior, reverseScrolling)