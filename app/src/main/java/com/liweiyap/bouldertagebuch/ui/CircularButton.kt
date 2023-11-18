package com.liweiyap.bouldertagebuch.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

@Composable
fun CircularButton(
    size: Dp,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(),
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        modifier = Modifier
            .size(size = size),
        colors = colors,
        elevation = elevation,
        enabled = isEnabled,
    ) {
        Text(
            text = text,
            style = textStyle,
            maxLines = 1,
            textAlign = TextAlign.Center,  // horizontal centering of the text
            modifier = Modifier
                .align(Alignment.CenterVertically),  // vertical centering of the Text composable
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CircularButtonPreview() {
    AppTheme {
        CircularButton(
            size = AppDimensions.todayRouteCountButtonSize,
            text = stringResource(id = R.string.button_bubble_today_route_count_add),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            isEnabled = false,
        ) {
        }
    }
}