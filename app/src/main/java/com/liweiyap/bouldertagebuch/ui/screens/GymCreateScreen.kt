package com.liweiyap.bouldertagebuch.ui.screens

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.gymNewIncomplete
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.services.SystemBroadcastReceiver
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.AppTextButtonCircular
import com.liweiyap.bouldertagebuch.ui.components.AppTextField
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.components.DifficultyColorIndicatorWithTooltip
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme
import com.liweiyap.bouldertagebuch.utils.getDate
import java.util.Collections

@Composable
fun GymCreateScreen(
    viewModel: MainViewModel,
) {
    SystemBroadcastReceiver(systemAction = Intent.ACTION_TIME_TICK) {
        viewModel.setCurrentDate(getDate())
    }

    GymCreateScreen(
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
    )
}

@Composable
private fun GymCreateScreen(
    userDefinedGym: Gym? = null,
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
                text = stringResource(id = if (userDefinedGym == null)
                    R.string.title_screen_gym_create
                else
                    R.string.title_screen_gym_edit),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Column(
                modifier = Modifier
                    .verticalScrollWithScrollbar(
                        state = rememberScrollState(),
                    ),
                verticalArrangement = Arrangement.spacedBy(AppDimensions.screenArrangementSpacing),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val newGymName = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(userDefinedGym?.name ?: "")) }

                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.textfield_enter_gym_name),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    input = newGymName.value,
                    onInputChanged = { newGymName.value = it },
                )

                if (newGymName.value.text.isNotBlank()) {
                    BubbleRouteEdit(userDefinedGym)
                }
            }
        }
    }
}

@Composable
private fun BubbleRouteEdit(
    userDefinedGym: Gym? = null,
) {
    BubbleLayout {
        if ((userDefinedGym == null) || userDefinedGym.difficulties.isEmpty()) {
            Text(
                text = stringResource(id = R.string.title_bubble_gym_add_routes),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        else {
            Text(
                text = stringResource(id = R.string.title_bubble_gym_edit_routes),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            val levels: ArrayList<ArrayList<Difficulty>> = remember(userDefinedGym) {
                userDefinedGym.getDifficultiesSortedByLevel()
            }

            val comparator: Comparator<ArrayList<Difficulty>> = remember(levels) {
                Comparator.comparing { level: ArrayList<Difficulty> ->
                    level.size
                }
            }

            val maxDifficultiesPerLevel: Int = remember(levels, comparator) {
                Collections.max(levels, comparator).size
            }

            for ((index, level) in levels.withIndex()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.width(
                            max(0.dp, AppDimensions.bubbleRouteEditImageSize.times(maxDifficultiesPerLevel) + AppDimensions.bubbleRouteEditImageSpacing.times(maxDifficultiesPerLevel - 1))
                        ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = AppDimensions.bubbleRouteEditImageSpacing,
                            alignment = Alignment.CenterHorizontally,
                        ),
                    ) {
                        for (difficulty in level) {
                            DifficultyColorIndicatorWithTooltip(
                                difficulty = difficulty,
                                size = AppDimensions.bubbleRouteEditImageSize,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1F))

                    AppTextButtonCircular(
                        size = AppDimensions.dialogDifficultySelectRouteCountButtonSize,
                        text = stringResource(id = R.string.button_remove),
                        textStyle = MaterialTheme.typography.bodyMedium,
                    ) {
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GymCreateScreenPreview() {
    AppTheme {
        GymCreateScreen(
            userDefinedGym = gymNewIncomplete,
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GymEditScreenPreview() {
    AppTheme {
        GymCreateScreen(
            userDefinedGym = gymVels,
        )
    }
}