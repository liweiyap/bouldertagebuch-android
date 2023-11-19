package com.liweiyap.bouldertagebuch.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.components.CircularButton
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainComposable()
            }
        }
    }
}

@Composable
private fun MainComposable() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.padding(AppDimensions.mainScreenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.mainScreenArrangementSpacing),
        ) {
            Text(
                text = stringResource(id = R.string.title_main),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            BubbleTodayRouteCount()
        }
    }
}

@Composable
private fun BubbleTodayRouteCount() {
    if (LocalInspectionMode.current) {
        BubbleTodayRouteCount(
            todayRouteCount = 0,
        )
    }
    else {
        BubbleTodayRouteCount(
            viewModel = viewModel(),
        )
    }
}

@Composable
private fun BubbleTodayRouteCount(
    viewModel: MainViewModel,
) {
    BubbleTodayRouteCount(
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value,
        onAddToCount = viewModel::addToCount,
        onRemoveFromCount = viewModel::removeFromCount,
    )
}

@Composable
private fun BubbleTodayRouteCount(
    todayRouteCount: Int,
    onAddToCount: () -> Unit = {},
    onRemoveFromCount: () -> Unit = {},
) {
    BubbleLayout {
        Text(
            text = stringResource(id = R.string.title_bubble_today_route_count),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$todayRouteCount",
                style = MaterialTheme.typography.displayLarge,
                maxLines = 1,
            )

            Text(
                text = "/10",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.weight(1F))

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_bubble_today_route_count_add),
            ) {
                onAddToCount()
            }

            Spacer(modifier = Modifier.width(AppDimensions.todayRouteCountButtonMargin))

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_bubble_today_route_count_remove),
                isEnabled = (todayRouteCount > 0),
            ) {
                onRemoveFromCount()
            }
        }
    }
}

@Composable
private fun BubbleTodayRouteCountButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    CircularButton(
        size = AppDimensions.todayRouteCountButtonSize,
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppDimensions.todayRouteCountButtonElevation,
            pressedElevation = AppDimensions.todayRouteCountButtonElevation,
            disabledElevation = AppDimensions.todayRouteCountButtonElevation,
        ),
        isEnabled = isEnabled,
    ) {
        onClick()
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainActivityPreview() {
    AppTheme {
        MainComposable()
    }
}