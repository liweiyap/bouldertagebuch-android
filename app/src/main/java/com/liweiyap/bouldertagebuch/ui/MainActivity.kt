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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.components.AppTextButtonCircular
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogDifficultySelect
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogGymSelect
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
    var doShowGymSelectDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var doShowDifficultySelectDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    MainComposable(
        onRequestGymSelectDialog = { doShowGymSelectDialog = true },
    )

    if (doShowGymSelectDialog) {
        DialogGymSelect(
            onDismissRequest = { doShowGymSelectDialog = false },
            viewModel = viewModel(),
            onRequestDifficultySelectDialog = { doShowDifficultySelectDialog = true }
        )
    }

    if (doShowDifficultySelectDialog) {
        DialogDifficultySelect(
            onDismissRequest = { doShowDifficultySelectDialog = false },
            viewModel = viewModel(),
        )
    }
}

@Composable
private fun MainComposable(
    onRequestGymSelectDialog: () -> Unit,
) {
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

            BubbleTodayRouteCount(
                onRequestGymSelectDialog = onRequestGymSelectDialog,
            )
        }
    }
}

@Composable
private fun BubbleTodayRouteCount(
    onRequestGymSelectDialog: () -> Unit = {},
) {
    if (LocalInspectionMode.current) {
        BubbleTodayRouteCount(
            todayGymId = GymId.UNKNOWN,
            todayRouteCount = arrayListOf(),
        )
    }
    else {
        BubbleTodayRouteCount(
            viewModel = viewModel(),
            onRequestGymSelectDialog = onRequestGymSelectDialog,
        )
    }
}

@Composable
private fun BubbleTodayRouteCount(
    viewModel: MainViewModel,
    onRequestGymSelectDialog: () -> Unit = {},
) {
    BubbleTodayRouteCount(
        todayGymId = viewModel.todayGymId.collectAsState().value,
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value,
        onAddToCount = {},
        onRemoveFromCount = {},
        onRequestGymSelectDialog = onRequestGymSelectDialog,
    )
}

@Composable
private fun BubbleTodayRouteCount(
    todayGymId: GymId,
    todayRouteCount: List<Int>,
    onAddToCount: () -> Unit = {},
    onRemoveFromCount: () -> Unit = {},
    onRequestGymSelectDialog: () -> Unit = {},
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
                text = "${todayRouteCount.sum()}",
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
                text = stringResource(id = R.string.button_route_count_add),
            ) {
                if (todayRouteCount.sum() == 0) {
                    onRequestGymSelectDialog()
                }
                else {
                    onAddToCount()
                }
            }

            Spacer(modifier = Modifier.width(AppDimensions.todayRouteCountButtonMargin))

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_route_count_remove),
                isEnabled = (todayRouteCount.sum() > 0),
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
    AppTextButtonCircular(
        size = AppDimensions.todayRouteCountButtonSize,
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium,
        isEnabled = isEnabled,
    ) {
        onClick()
    }
}

@Composable
fun DialogGymSelect(
    onDismissRequest: () -> Unit,
    viewModel: MainViewModel,
    onRequestDifficultySelectDialog: () -> Unit,
) {
    DialogGymSelect(
        onDismissRequest = onDismissRequest,
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        onGymSelected = viewModel::setTodayGymId,
        onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
    )
}

@Composable
fun DialogDifficultySelect(
    onDismissRequest: () -> Unit,
    viewModel: MainViewModel,
) {
    val gymId: GymId by viewModel.todayGymId.collectAsState()
    val userDefinedGym: Gym? by viewModel.userDefinedGym.collectAsState()

    val gym: Gym? = remember(gymId, userDefinedGym) {
        when (gymId) {
            gymRockerei.id -> gymRockerei
            gymVels.id -> gymVels
            userDefinedGym?.id -> userDefinedGym
            else -> null
        }
    }

    DialogDifficultySelect(
        onDismissRequest = onDismissRequest,
        gym = gym,
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value,
        onPositiveButtonClicked = viewModel::setTodayRouteCount,
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainActivityPreview() {
    AppTheme {
        MainComposable()
    }
}