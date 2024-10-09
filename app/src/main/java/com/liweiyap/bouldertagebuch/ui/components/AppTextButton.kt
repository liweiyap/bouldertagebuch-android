package com.liweiyap.bouldertagebuch.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight = FontWeight.Normal,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    iconVector: ImageVector? = null,
    shape: Shape = ButtonDefaults.shape,
    elevation: Dp = AppDimensions.buttonElevation,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            disabledElevation = elevation,
        ),
        enabled = isEnabled,
    ) {
        Text(
            text = text,
            style = textStyle,
            fontWeight = fontWeight,
            maxLines = maxLines,
            overflow = overflow,
            textAlign = TextAlign.Center,  // horizontal centering of the text
            modifier = Modifier
                .align(Alignment.CenterVertically),  // vertical centering of the Text composable
        )

        if (iconVector != null) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun AppTextButtonCircular(
    size: Dp,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    elevation: Dp = AppDimensions.buttonElevation,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    AppTextButton(
        modifier = Modifier
            .size(size = size),
        text = text,
        textStyle = textStyle,
        contentPadding = PaddingValues(all = 0.dp),
        maxLines = 1,
        shape = CircleShape,
        elevation = elevation,
        isEnabled = isEnabled,
        onClick = onClick,
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextButtonCircularPreview() {
    AppTheme {
        AppTextButtonCircular(
            size = AppDimensions.todayRouteCountButtonSize,
            text = stringResource(id = R.string.button_add),
            textStyle = MaterialTheme.typography.bodyMedium,
            isEnabled = false,
        ) {
        }
    }
}