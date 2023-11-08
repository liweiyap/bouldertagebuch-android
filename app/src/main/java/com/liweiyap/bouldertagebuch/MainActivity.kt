package com.liweiyap.bouldertagebuch

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.liweiyap.bouldertagebuch.ui.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.CircularButton
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComposable()
        }
    }
}

@Composable
private fun MainComposable() {
    AppTheme {
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
}

@Composable
private fun BubbleTodayRouteCount() {
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
                text = "0",
                style = MaterialTheme.typography.displayLarge,
                maxLines = 1,
            )

            Text(
                text = "/10",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.weight(1F))

            CircularButton(
                size = AppDimensions.todayRouteCountButtonSize,
                text = stringResource(id = R.string.button_bubble_today_route_count_add),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
            }

            Spacer(modifier = Modifier.width(AppDimensions.todayRouteCountButtonMargin))

            CircularButton(
                size = AppDimensions.todayRouteCountButtonSize,
                text = stringResource(id = R.string.button_bubble_today_route_count_remove),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainActivityPreview() {
    MainComposable()
}