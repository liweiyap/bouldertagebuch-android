package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    input: TextFieldValue,
    onInputChanged: (TextFieldValue) -> Unit,
) {
    TextField(
        modifier = modifier,
        placeholder = placeholder,
        singleLine = true,
        value = input,
        onValueChange = onInputChanged,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
            cursorColor = MaterialTheme.colorScheme.onTertiary,
            focusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
        ),
        shape = MaterialTheme.shapes.small,
    )
}