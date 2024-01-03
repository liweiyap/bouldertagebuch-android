package com.liweiyap.bouldertagebuch.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

/**
 * Helper func to rotate TextView but not perfect, because a lot of measurement, and in turn
 * truncation, hyphenation, etc., is done before rotation, causing us to see some weird UI effects
 * if the text used is too long
 */
fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }