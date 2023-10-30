package com.liweiyap.bouldertagebuch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.liweiyap.bouldertagebuch.ui.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityComposable()
        }
    }
}

@Composable
private fun MainActivityComposable() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.padding(AppDimensions.mainScreenPadding),
            ) {
                Text(
                    text = stringResource(id = R.string.title_main),
                    style = MaterialTheme.typography.titleLarge,
                )

                BubbleLayout {
                    Text(
                        text = stringResource(id = R.string.title_bubble_today_route_count),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainActivityPreview() {
    MainActivityComposable()
}