package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    title: String,
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
                Text(
                    modifier = Modifier.padding(bottom = AppDimensions.dialogTitleMarginBottom),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Column(
                    modifier = Modifier
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
        }
    }
}