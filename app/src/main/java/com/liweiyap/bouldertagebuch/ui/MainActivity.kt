package com.liweiyap.bouldertagebuch.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.components.AppTextButtonCircular
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.components.DifficultyColorIndicator
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogDifficultySelect
import com.liweiyap.bouldertagebuch.ui.dialogs.DialogGymSelect
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

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
        onRequestDifficultySelectDialog = { doShowDifficultySelectDialog = true },
    )

    if (doShowGymSelectDialog) {
        DialogGymSelect(
            onDismissRequest = { doShowGymSelectDialog = false },
            viewModel = viewModel(),
            onRequestDifficultySelectDialog = { doShowDifficultySelectDialog = true },
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
    onRequestDifficultySelectDialog: () -> Unit,
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

            Column(
                modifier = Modifier
                    .verticalScrollWithScrollbar(
                        state = rememberScrollState(),
                        scrollbarConfig = ScrollBarConfig(
                            doFade = false,
                        )
                    ),
            ) {
                BubbleTodayRouteCount(
                    onRequestGymSelectDialog = onRequestGymSelectDialog,
                    onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
                )
            }
        }
    }
}

@Composable
private fun BubbleTodayRouteCount(
    onRequestGymSelectDialog: () -> Unit = {},
    onRequestDifficultySelectDialog: () -> Unit = {},
) {
    if (LocalInspectionMode.current) {
        BubbleTodayRouteCount(
            todayGymId = GymId.VELS,
            todayRouteCount = ArrayList(Collections.nCopies(gymVels.getDifficultiesSortedByLevel().size, 1)),
        )
    }
    else {
        BubbleTodayRouteCount(
            viewModel = viewModel(),
            onRequestGymSelectDialog = onRequestGymSelectDialog,
            onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
        )
    }
}

@Composable
private fun BubbleTodayRouteCount(
    viewModel: MainViewModel,
    onRequestGymSelectDialog: () -> Unit = {},
    onRequestDifficultySelectDialog: () -> Unit = {},
) {
    BubbleTodayRouteCount(
        todayGymId = viewModel.todayGymId.collectAsState().value,
        todayRouteCount = viewModel.todayRouteCount.collectAsState().value,
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        onRequestGymSelectDialog = onRequestGymSelectDialog,
        onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
    )
}

@Composable
private fun BubbleTodayRouteCount(
    todayGymId: GymId,
    todayRouteCount: List<Int>,
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

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_route_count_add),
            ) {
                if (todayRouteCount.sum() == 0) {
                    onRequestGymSelectDialog()
                }
                else {
                    onRequestDifficultySelectDialog()
                }
            }

            Spacer(modifier = Modifier.width(AppDimensions.todayRouteCountButtonMargin))

            BubbleTodayRouteCountButton(
                text = stringResource(id = R.string.button_route_count_remove),
                isEnabled = (todayRouteCount.sum() > 0),
            ) {
                onRequestDifficultySelectDialog()
            }
        }

        if ((todayGym != null) && (todayRouteCount.sum() > 0)) {
            BubbleTodayRouteCountFlow(
                todayGym = todayGym,
                todayRouteCount = todayRouteCount,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BubbleTodayRouteCountFlow(
    todayGym: Gym,
    todayRouteCount: List<Int>,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.todayRouteCountFlowSpacingHorizontal),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.todayRouteCountFlowSpacingVertical),
    ) {
        val levels: ArrayList<ArrayList<Difficulty>> = remember(todayGym) {
            todayGym.getDifficultiesSortedByLevel()
        }

        for ((index, level) in levels.withIndex()) {
            BubbleTodayRouteCountFlowItem(
                index = index,
                level = level,
                todayRouteCount = todayRouteCount,
            )
        }
    }
}

@Composable
private fun BubbleTodayRouteCountFlowItem(
    index: Int,
    level: ArrayList<Difficulty>,
    todayRouteCount: List<Int>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.todayRouteCountFlowItemSpacing),
        ) {
            for (difficulty in level) {
                DifficultyColorIndicator(
                    difficulty = difficulty,
                    size = AppDimensions.todayRouteCountFlowItemSize,
                )
            }
        }

        Text(
            text = todayRouteCount[index].toString(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
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
        onRouteCountIncreased = viewModel::increaseTodayRouteCount,
        onRouteCountDecreased = viewModel::decreaseTodayRouteCount,
        onRouteCountZero = viewModel::clearTodayRouteCount,
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