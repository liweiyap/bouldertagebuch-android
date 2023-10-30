package com.liweiyap.bouldertagebuch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

@Composable
fun BubbleLayout(
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.large),
    ) {
        Column (
            modifier = Modifier.padding(AppDimensions.bubblePadding),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun BubbleLayoutPreview() {
    AppTheme {
        BubbleLayout() {
            Text(
                text = stringResource(id = R.string.title_bubble_today_route_count),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}