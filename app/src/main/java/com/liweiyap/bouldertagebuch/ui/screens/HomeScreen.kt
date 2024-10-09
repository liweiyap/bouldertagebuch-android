package com.liweiyap.bouldertagebuch.ui.screens

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.services.SystemBroadcastReceiver
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.AppTextButton
import com.liweiyap.bouldertagebuch.ui.components.AppTextButtonCircular
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.components.BubbleRouteCountFlowRow
import com.liweiyap.bouldertagebuch.ui.components.LifecycleOwner
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogDifficultySelect
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogGymSelect
import com.liweiyap.bouldertagebuch.ui.navigation.AppNavHostController
import com.liweiyap.bouldertagebuch.ui.theme.AppColor
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import com.liweiyap.bouldertagebuch.utils.getDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.util.Collections

@Composable
fun HomeScreen(
    appNavHostController: AppNavHostController,
    viewModel: MainViewModel,
) {
    SystemBroadcastReceiver(systemAction = Intent.ACTION_TIME_TICK) {
        viewModel.setCurrentDate(getDate())
    }

    LifecycleOwner {
        viewModel.setCurrentDate(getDate())
    }

    var doShowGymSelectDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var doShowDifficultySelectDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    HomeScreen(
        todayGymId = viewModel.todayGymId.collectAsState().value,
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value.toImmutableList(),
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        onRequestGymSelectDialog = { doShowGymSelectDialog = true },
        onRequestDifficultySelectDialog = { doShowDifficultySelectDialog = true },
        onRequestNavigateToHistory = appNavHostController::navigateToHistory,
    )

    if (doShowGymSelectDialog) {
        DialogGymSelect(
            onDismissRequest = { doShowGymSelectDialog = false },
            viewModel = viewModel,
            onRequestDifficultySelectDialog = { doShowDifficultySelectDialog = true },
        )
    }

    if (doShowDifficultySelectDialog) {
        DialogDifficultySelect(
            onDismissRequest = { doShowDifficultySelectDialog = false },
            viewModel = viewModel,
        )
    }
}

@Composable
private fun HomeScreen(
    todayGymId: GymId,
    todayRouteCount: ImmutableList<Int>,
    userDefinedGym: Gym? = null,
    onRequestGymSelectDialog: () -> Unit = {},
    onRequestDifficultySelectDialog: () -> Unit = {},
    onRequestNavigateToHistory: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.padding(AppDimensions.screenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.screenArrangementSpacing),
        ) {
            Text(
                text = stringResource(id = R.string.title_screen_home),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Column(
                modifier = Modifier
                    .verticalScrollWithScrollbar(
                        state = rememberScrollState(),
                        scrollbarConfig = ScrollBarConfig(
                            doFade = false,
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(AppDimensions.screenArrangementSpacing),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BubbleTodayRouteCount(
                    todayGymId = todayGymId,
                    todayRouteCount = todayRouteCount,
                    userDefinedGym = userDefinedGym,
                    onRequestGymSelectDialog = onRequestGymSelectDialog,
                    onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
                )

                NavigateToHistoryButton(
                    onRequestNavigation = onRequestNavigateToHistory,
                )

                AuthorNote(
                    modifier = Modifier.padding(top = AppDimensions.screenArrangementSpacing),
                )
            }
        }
    }
}

@Composable
private fun BubbleTodayRouteCount(
    todayGymId: GymId,
    todayRouteCount: ImmutableList<Int>,
    userDefinedGym: Gym? = null,
    onRequestGymSelectDialog: () -> Unit = {},
    onRequestDifficultySelectDialog: () -> Unit = {},
) {
    val todayGym: Gym? = remember(todayGymId, userDefinedGym) {
        when (todayGymId) {
            gymRockerei.id -> gymRockerei
            gymVels.id -> gymVels
            userDefinedGym?.id -> userDefinedGym
            else -> null
        }
    }

    BubbleLayout {
        Text(
            text = stringResource(id = R.string.title_bubble_today_route_count) + (if (todayGym == null)
                ""
            else
                " (${todayGym.name})"
            ),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
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

            val addAction: () -> Unit = remember(todayRouteCount) {
                if (todayRouteCount.sum() == 0) {
                    onRequestGymSelectDialog
                }
                else {
                    onRequestDifficultySelectDialog
                }
            }
            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_route_count_add),
                onClick = addAction,
            )

            Spacer(modifier = Modifier.width(AppDimensions.todayRouteCountButtonMargin))

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_route_count_remove),
                isEnabled = (todayRouteCount.sum() > 0),
                onClick = onRequestDifficultySelectDialog,
            )
        }

        if ((todayGym != null) && (todayRouteCount.sum() > 0)) {
            BubbleRouteCountFlowRow(
                gym = todayGym,
                routeCount = todayRouteCount,
            )
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
        onClick = onClick,
    )
}

@Composable
private fun NavigateToHistoryButton(
    onRequestNavigation: () -> Unit = {},
) {
    AppTextButton(
        modifier = Modifier
            .widthIn(
                min = AppDimensions.navigateToHistoryButtonMinWidth,
                max = AppDimensions.navigateToHistoryButtonMaxWidth,
            ),
        text = stringResource(id = R.string.button_navigate_history),
        textStyle = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        shape = MaterialTheme.shapes.medium,
        onClick = onRequestNavigation,
    )
}

@Composable
private fun AuthorNote(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.authorNoteSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.climbing_minified),
            contentDescription = "",
            modifier = Modifier.size(size = AppDimensions.homeBackgroundImageSize),
            tint = AppColor.getHomeBackgroundIconColor(isSystemInDarkTheme()),
        )

        Text(
            text = stringResource(id = R.string.author_note),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            todayGymId = GymId.VELS,
            todayRouteCount = ArrayList(Collections.nCopies(gymVels.getDifficultiesSortedByLevel().size, 1)).toImmutableList(),
        )
    }
}