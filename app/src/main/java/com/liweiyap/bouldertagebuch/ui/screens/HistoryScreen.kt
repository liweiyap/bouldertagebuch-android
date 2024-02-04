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
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.HistoryHeatMapCalendar
import com.liweiyap.bouldertagebuch.ui.components.ScrollBarConfig
import com.liweiyap.bouldertagebuch.ui.components.Spinner
import com.liweiyap.bouldertagebuch.ui.components.verticalScrollWithScrollbar
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.utils.getDate
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
        onItemSelected = viewModel::setViewedYear,
    )
}

@Composable
private fun HistoryScreen(
    years: List<Int>,
    viewedYear: Int,
    paginatedLog: Map<LocalDate, Pair<GymId, List<Int>>>,
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
                Spinner(
                    modifier = Modifier.align(Alignment.End),
                    title = viewedYear.toString(),
                    items = years,
                    onItemSelected = onItemSelected,
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
                )
            }
        }
    }
}