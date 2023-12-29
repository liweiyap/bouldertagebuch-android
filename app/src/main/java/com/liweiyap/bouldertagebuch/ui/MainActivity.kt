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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.components.AppDialog
import com.liweiyap.bouldertagebuch.ui.components.AppTextButton
import com.liweiyap.bouldertagebuch.ui.components.AppTextButtonCircular
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
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
            userDefinedGym = null,
            todayGymId = GymId.UNKNOWN,
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
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        todayGymId = viewModel.todayGymId.collectAsState().value,
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value,
        onAddToCount = viewModel::addToCount,
        onRemoveFromCount = viewModel::removeFromCount,
        onGymSelected = viewModel::setTodayGymId,
    )
}

@Composable
private fun BubbleTodayRouteCount(
    userDefinedGym: Gym?,
    todayGymId: GymId,
    todayRouteCount: Int,
    onAddToCount: () -> Unit = {},
    onRemoveFromCount: () -> Unit = {},
    onGymSelected:(GymId) -> Unit = {},
) {
    var doShowGymSelectDialog by rememberSaveable { mutableStateOf(false) }

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
                if (todayRouteCount == 0) {
                    doShowGymSelectDialog = true
                }
                else {
                    onAddToCount()
                }
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

    if (doShowGymSelectDialog) {
        DialogGymSelect(
            onDismissRequest = { doShowGymSelectDialog = false },
            userDefinedGym = userDefinedGym,
            onGymSelected = onGymSelected,
        )
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
private fun DialogGymSelect(
    onDismissRequest: () -> Unit,
    userDefinedGym: Gym?,
    onGymSelected:(GymId) -> Unit,
) {
    AppDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.title_dialog_gym_select),
    ) {
        DialogGymSelectButton(
            text = gymRockerei.name,
            marginBottom = 8.dp,
        ) {
            onGymSelected(GymId.ROCKEREI)
        }

        DialogGymSelectButton(
            text = gymVels.name,
            marginBottom = 24.dp,
        ) {
            onGymSelected(GymId.VELS)
        }

        if (userDefinedGym == null) {
            DialogGymSelectButton(
                text = stringResource(id = R.string.button_dialog_gym_select_create_new).uppercase(),
                fontWeight = FontWeight.Bold,
            )
        }
        else {
            DialogGymSelectButton(
                text = userDefinedGym.name,
            )
        }
    }
}

@Composable
private fun DialogGymSelectButton(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    marginBottom: Dp = 0.dp,
    onGymSelected:() -> Unit = {},
) {
    AppTextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = marginBottom),
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        shape = MaterialTheme.shapes.medium,
    ) {
        onGymSelected()
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

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DialogGymSelectionPreview() {
    AppTheme {
        DialogGymSelect(
            onDismissRequest = {},
            userDefinedGym = null,
            onGymSelected = {},
        )
    }
}