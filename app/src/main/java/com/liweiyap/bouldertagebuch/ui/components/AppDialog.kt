package com.liweiyap.bouldertagebuch.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    title: String,
    positiveButton: Pair<String, (() -> Unit)>? = null,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier
                .sizeIn(
                    minWidth = AppDimensions.dialogMinWidth,
                    maxWidth = AppDimensions.dialogMaxWidth,
                ),
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier = Modifier
                    .padding(AppDimensions.dialogPadding),
            ) {
                AppDialogTitle(
                    text = title,
                )

                AppDialogContent {
                    content()
                }

                AppDialogAlertButtonLayout(
                    positiveButton = positiveButton,
                )
            }
        }
    }
}

@Composable
private fun AppDialogTitle(
    text: String,
) {
    Text(
        modifier = Modifier.padding(bottom = AppDimensions.dialogContentsMarginVertical),
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            hyphens = Hyphens.Auto
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun AppDialogContent(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScrollWithScrollbar(
                state = rememberScrollState(),
                scrollbarConfig = ScrollBarConfig(
                    doFade = false,
                )
            )
    ) {
        content()
    }
}

@Composable
private fun AppDialogAlertButtonLayout(
    positiveButton: Pair<String, (() -> Unit)>? = null,
) {
    if (positiveButton != null) {
        Row(
            modifier = Modifier
                .padding(top = AppDimensions.dialogContentsMarginVertical)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            AppDialogAlertButtonPositive(
                text = positiveButton.first
            ) {
                positiveButton.second()
            }
        }
    }
}

@Composable
private fun AppDialogAlertButtonPositive(
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isPressed)
                MaterialTheme.colorScheme.tertiary.copy(alpha = AppDimensions.dialogButtonPressedAlpha)
            else
                Color.Transparent,
        ),
        interactionSource = interactionSource,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AppDialogPreview() {
    AppTheme {
        AppDialog(
            onDismissRequest = {},
            title = "Dialog Title",
            positiveButton = Pair(stringResource(id = R.string.button_dialog_positive_default)) {}
        ) {
            Text(
                text = "Dialog Message",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}