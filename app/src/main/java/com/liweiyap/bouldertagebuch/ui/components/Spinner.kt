package com.liweiyap.bouldertagebuch.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun <T> Spinner(
    modifier: Modifier = Modifier,
    title: String,
    items: ImmutableList<T>,
    itemToString: (T) -> String = { it.toString() },
    viewedItem: T,
    onItemSelected: (T) -> Unit = {},
) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
    ) {
        AppTextButton(
            text = title,
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            iconVector = Icons.Filled.ArrowDropDown,
            shape = MaterialTheme.shapes.medium,
        ) {
            isExpanded = !isExpanded
        }

        // if items is empty list, then no dropdown menu appears
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemToString(item),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    leadingIcon = {
                        if (item == viewedItem) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                            )
                        }
                    },
                    onClick = {
                        isExpanded = false
                        onItemSelected(item)
                    },
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SpinnerPreview() {
    AppTheme {
        Spinner(
            items = listOf("2024").toImmutableList(),
            viewedItem = "2024",
            title = "Year: 2024",
        )
    }
}