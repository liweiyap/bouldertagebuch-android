package com.liweiyap.bouldertagebuch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.BubbleLayout
import com.liweiyap.bouldertagebuch.ui.components.HistoryHeatMapCalendar
import com.liweiyap.bouldertagebuch.ui.components.BubbleRouteCountFlowRow
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.Spinner
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.utils.getDate
import com.liweiyap.bouldertagebuch.utils.short
import com.liweiyap.bouldertagebuch.utils.toJava
import com.liweiyap.bouldertagebuch.utils.toKotlin
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun HistoryScreen(
    viewModel: MainViewModel,
) {
    HistoryScreen(
        years = viewModel.years.collectAsState().value,
        viewedYear = viewModel.viewedYear.collectAsState().value,
        paginatedLog = viewModel.paginatedLog.collectAsState().value,
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        onItemSelected = viewModel::setViewedYear,
    )
}

@Composable
private fun HistoryScreen(
    years: List<Int>,
    viewedYear: Int,
    paginatedLog: Map<LocalDate, Pair<GymId, List<Int>>>,
    userDefinedGym: Gym? = null,
    onItemSelected: (Int) -> Unit,
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
                text = stringResource(id = R.string.title_screen_history),
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
                // implements Serializable in Java, allowing us to use rememberSaveable
                var selectedDate: java.time.LocalDate? by rememberSaveable { mutableStateOf(null) }

                Spinner(
                    modifier = Modifier.align(Alignment.End),
                    title = viewedYear.toString(),
                    items = years,
                    onItemSelected = { year ->
                        onItemSelected.invoke(year)
                        selectedDate = null
                    },
                )

                val today: LocalDate = getDate()
                val endDate: LocalDate = remember(viewedYear) {
                    if (viewedYear == today.year) {
                        today
                    }
                    else {
                        LocalDate(year = viewedYear, monthNumber = 12, dayOfMonth = 31)
                    }
                }
                val startDate: LocalDate = remember(endDate) {
                    if (viewedYear == today.year) {
                        endDate.minus(value = 12, unit = DateTimeUnit.MONTH).plus(value = 1, unit = DateTimeUnit.DAY)
                    }
                    else {
                        LocalDate(year = viewedYear, monthNumber = 1, dayOfMonth = 1)
                    }
                }

                HistoryHeatMapCalendar(
                    paginatedLog = paginatedLog,
                    startDate = startDate,
                    endDate = endDate,
                    onDateSelected = { date ->
                        selectedDate = date.toJava()
                    },
                )

                selectedDate?.let {
                    BubbleSelectedDayRouteCount(
                        paginatedLog = paginatedLog,
                        userDefinedGym = userDefinedGym,
                        selectedDate = it,
                    )
                }
            }
        }
    }
}

@Composable
private fun BubbleSelectedDayRouteCount(
    paginatedLog: Map<LocalDate, Pair<GymId, List<Int>>>,
    userDefinedGym: Gym? = null,
    selectedDate: java.time.LocalDate,
) {
    BubbleLayout {
        Text(
            text = "${selectedDate.dayOfMonth}. ${selectedDate.month.short(LocalContext.current)} ${selectedDate.year}",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        val selectedDateEntry: Pair<GymId, List<Int>>? = paginatedLog[selectedDate.toKotlin()]
        if (selectedDateEntry == null) {
            Text(
                text = stringResource(id = R.string.bubble_selected_heatmap_entry_blank),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        else {
            val selectedDateGym: Gym? = when (selectedDateEntry.first) {
                gymRockerei.id -> gymRockerei
                gymVels.id -> gymVels
                userDefinedGym?.id -> userDefinedGym
                else -> null
            }
            val selectedDateRouteCount = selectedDateEntry.second.sum()

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedDateRouteCount.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    maxLines = 1,
                )

                if (selectedDateGym != null) {
                    Text(
                        text = selectedDateGym.name,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = AppDimensions.bubbleSelectedDateGymNameSpacing),
                    )
                }
            }

            if ((selectedDateGym != null) && (selectedDateRouteCount > 0)) {
                BubbleRouteCountFlowRow(
                    gym = selectedDateGym,
                    routeCount = selectedDateEntry.second,
                )
            }
        }
    }
}