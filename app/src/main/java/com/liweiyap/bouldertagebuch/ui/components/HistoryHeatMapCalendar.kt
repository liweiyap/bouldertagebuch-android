// hack to work around HeatMapCalendarState having internal constructor
@file:Suppress("INVISIBLE_MEMBER")

package com.liweiyap.bouldertagebuch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import com.kizitonwose.calendar.compose.CalendarItemInfo
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.HeatMapCalendar
import com.kizitonwose.calendar.compose.heatmapcalendar.HeatMapCalendarState
import com.kizitonwose.calendar.compose.heatmapcalendar.HeatMapWeek
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.ui.theme.AppColor
import com.liweiyap.bouldertagebuch.ui.theme.AppDimensions
import com.liweiyap.bouldertagebuch.utils.short
import com.liweiyap.bouldertagebuch.utils.toKotlin
import com.liweiyap.bouldertagebuch.utils.yearMonthToJava
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

enum class HistoryHeatMapQuartile {
    NONE,
    FIRST,
    SECOND,
    THIRD,
    FOURTH;

    fun getColor(isSystemInDarkTheme: Boolean): Color {
        return AppColor.getHeatMapColor(this, isSystemInDarkTheme)
    }
}

private fun mapRouteCountToHeat(
    log: Map<LocalDate, Pair<GymId, List<Int>>>,
): Map<LocalDate, HistoryHeatMapQuartile> {
    val maxDailyRouteCountEntry: Map.Entry<LocalDate, Pair<GymId, List<Int>>> = log.maxByOrNull { entry ->
        entry.value.second.sum()
    } ?: return emptyMap()

    val maxDailyRouteCount: Int = maxDailyRouteCountEntry.value.second.sum()
    return log.mapValues { entry ->
        val dailyRouteCount: Int = entry.value.second.sum()
        val dailyRouteCountPercentile: Float = dailyRouteCount.toFloat() / maxDailyRouteCount

        if (dailyRouteCount == 0) {
            HistoryHeatMapQuartile.NONE
        }
        else if (dailyRouteCountPercentile < 0.25F) {
            HistoryHeatMapQuartile.FIRST
        }
        else if (dailyRouteCountPercentile < 0.5F) {
            HistoryHeatMapQuartile.SECOND
        }
        else if (dailyRouteCountPercentile < 0.75F) {
            HistoryHeatMapQuartile.THIRD
        }
        else {
            HistoryHeatMapQuartile.FOURTH
        }
    }
}

@Composable
fun HistoryHeatMapCalendar(
    paginatedLog: Map<LocalDate, Pair<GymId, List<Int>>>,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    Column {
        val heatLog: Map<LocalDate, HistoryHeatMapQuartile> = mapRouteCountToHeat(paginatedLog)

        val heatMapCalendarState = rememberSaveable(startDate, endDate, saver = HeatMapCalendarState.Saver) {
            HeatMapCalendarState(
                startMonth = startDate.yearMonthToJava(),
                endMonth = endDate.yearMonthToJava(),
                firstVisibleMonth = endDate.yearMonthToJava(),
                firstDayOfWeek = firstDayOfWeekFromLocale(),
                visibleItemState = null,
            )
        }

        HeatMapCalendar(
            modifier = Modifier.padding(bottom = AppDimensions.heatMapCalendarMarginVertical),
            state = heatMapCalendarState,
            contentPadding = PaddingValues(end = AppDimensions.heatMapCalendarScrollEndPadding),
            dayContent = { calendarDay, heatMapWeek ->
                val currentDate: LocalDate = calendarDay.date.toKotlin()

                HistoryHeatMapCalendarDay(
                    currentDate = currentDate,
                    heatMapWeek = heatMapWeek,
                    startDate = startDate,
                    endDate = endDate,
                    quartile = heatLog[currentDate] ?: HistoryHeatMapQuartile.NONE,
                ) {
                }
            },
            weekHeader = { dayOfWeek ->
                HistoryHeatMapCalendarWeekHeader(dayOfWeek)
            },
            monthHeader = { calendarMonth ->
                HistoryHeatMapCalendarMonthHeader(calendarMonth, endDate, heatMapCalendarState)
            },
        )

        HistoryHeatMapLegend(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.heatMapCalendarLegendMarginHorizontal),
        )
    }
}

@Composable
private fun HistoryHeatMapCalendarDay(
    currentDate: LocalDate,
    heatMapWeek: HeatMapWeek,
    startDate: LocalDate,
    endDate: LocalDate,
    quartile: HistoryHeatMapQuartile,
    onClick: (LocalDate) -> Unit,
) {
    // We only want to draw boxes on the days that are in the
    // past 12 months. Since the calendar is month-based, we ignore
    // the future dates in the current month and those in the start
    // month that are older than 12 months from today.
    // We draw a transparent box on the empty spaces in the first week
    // so the items are laid out properly as the column is top to bottom.
    val weekDates: List<LocalDate> = heatMapWeek.days.map { it.date.toKotlin() }

    if (currentDate in startDate .. endDate) {
        HistoryHeatMapQuartileColoredCell(
            color = quartile.getColor(isSystemInDarkTheme()),
        ) {
            onClick(currentDate)
        }
    }
    else if (weekDates.contains(startDate)) {
        HistoryHeatMapQuartileColoredCell(
            color = Color.Transparent,
        )
    }
}

@Composable
private fun HistoryHeatMapQuartileColoredCell(
    color: Color,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .size(AppDimensions.heatMapCalendarCellSize)
            .padding(AppDimensions.heatMapCalendarCellPadding)
            .clip(MaterialTheme.shapes.extraSmall)
            .background(color = color)
            .clickable(enabled = (onClick != null)) { onClick?.invoke() },
    )
}

@Composable
private fun HistoryHeatMapLegend(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = stringResource(id = R.string.heatmap_quartile_min_label),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        HistoryHeatMapQuartile.entries.forEach { level ->
            HistoryHeatMapQuartileColoredCell(
                color = level.getColor(isSystemInDarkTheme()),
            )
        }

        Text(
            text = stringResource(id = R.string.heatmap_quartile_max_label),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun HistoryHeatMapCalendarWeekHeader(
    dayOfWeek: DayOfWeek,
) {
    Box(
        modifier = Modifier
            .height(AppDimensions.heatMapCalendarCellSize)
            .padding(horizontal = AppDimensions.heatMapCalendarWeekHeaderMarginHorizontal),
    ) {
        Text(
            text = dayOfWeek.short(LocalContext.current),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun HistoryHeatMapCalendarMonthHeader(
    calendarMonth: CalendarMonth,
    endDate: LocalDate,
    state: HeatMapCalendarState,
) {
    val density: Density = LocalDensity.current
    val firstFullyVisibleMonth: java.time.YearMonth? by remember {
        derivedStateOf { getFirstMonthOutOfBounds(state.layoutInfo, density) }
    }

    if (calendarMonth.weekDays.first().first().date.toKotlin() <= endDate) {
        val yearMonth: java.time.YearMonth = calendarMonth.yearMonth

        val titleBuilder = StringBuilder()
        titleBuilder.append(yearMonth.month.short(LocalContext.current))
        if (yearMonth == firstFullyVisibleMonth) {
            titleBuilder.append(" '${yearMonth.year % 100}")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = AppDimensions.heatMapCalendarMonthHeaderMarginBottom,
                    start = AppDimensions.heatMapCalendarMonthHeaderMarginStart,
                ),
        ) {
            Text(
                text = titleBuilder.toString(),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Find the first index with at most one cell out of bounds.
 */
private fun getFirstMonthOutOfBounds(
    layoutInfo: CalendarLayoutInfo,
    density: Density,
): java.time.YearMonth? {
    val visibleItemsInfo: List<CalendarItemInfo> = layoutInfo.visibleMonthsInfo
    return when {
        visibleItemsInfo.isEmpty() -> null
        visibleItemsInfo.count() == 1 -> visibleItemsInfo.first().month.yearMonth
        else -> {
            val firstItem: CalendarItemInfo = visibleItemsInfo.first()
            val daySizePx: Float = with(density) { AppDimensions.heatMapCalendarCellSize.toPx() }
            if ((firstItem.size < daySizePx * 3) ||  // Ensure the Month + Year text can fit.
                ((firstItem.offset < layoutInfo.viewportStartOffset) &&  // Ensure the week row size - 1 is visible.
                (layoutInfo.viewportStartOffset - firstItem.offset > daySizePx))
            ) {
                visibleItemsInfo[1].month.yearMonth
            }
            else {
                firstItem.month.yearMonth
            }
        }
    }
}