package com.liweiyap.bouldertagebuch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.kizitonwose.calendar.compose.heatmapcalendar.rememberHeatMapCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.HistoryHeatMapCalendar
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.utils.getDate
import com.liweiyap.bouldertagebuch.utils.yearMonthToJava
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

@Composable
fun HistoryScreen(
    viewModel: MainViewModel,
) {
    HistoryScreen(
        paginatedLog = viewModel.paginatedLog.collectAsState().value,
    )
}

@Composable
private fun HistoryScreen(
    paginatedLog: Map<LocalDate, Pair<GymId, List<Int>>>,
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
                val endDate: LocalDate = remember { getDate() }
                val startDate: LocalDate = remember { endDate.minus(value = 12, unit = DateTimeUnit.MONTH) }
                val state = rememberHeatMapCalendarState(
                    startMonth = startDate.yearMonthToJava(),
                    endMonth = endDate.yearMonthToJava(),
                    firstVisibleMonth = endDate.yearMonthToJava(),
                    firstDayOfWeek = firstDayOfWeekFromLocale(),
                )

                HistoryHeatMapCalendar(
                    state = state,
                    paginatedLog = paginatedLog,
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        }
    }
}