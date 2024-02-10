package com.liweiyap.bouldertagebuch.utils

import android.content.Context
import android.content.res.Resources
import com.liweiyap.bouldertagebuch.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun getDate(): LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}

fun DayOfWeek.short(context: Context): String {
    val resources: Resources = context.resources
    return when (this) {
        DayOfWeek.MONDAY -> resources.getString(R.string.monday_short)
        DayOfWeek.TUESDAY -> resources.getString(R.string.tuesday_short)
        DayOfWeek.WEDNESDAY -> resources.getString(R.string.wednesday_short)
        DayOfWeek.THURSDAY -> resources.getString(R.string.thursday_short)
        DayOfWeek.FRIDAY -> resources.getString(R.string.friday_short)
        DayOfWeek.SATURDAY -> resources.getString(R.string.saturday_short)
        DayOfWeek.SUNDAY -> resources.getString(R.string.sunday_short)
    }
}

fun java.time.Month.short(context: Context): String {
    val resources: Resources = context.resources
    return when (this) {
        java.time.Month.JANUARY -> resources.getString(R.string.january_short)
        java.time.Month.FEBRUARY -> resources.getString(R.string.february_short)
        java.time.Month.MARCH -> resources.getString(R.string.march_short)
        java.time.Month.APRIL -> resources.getString(R.string.april_short)
        java.time.Month.MAY -> resources.getString(R.string.may_short)
        java.time.Month.JUNE -> resources.getString(R.string.june_short)
        java.time.Month.JULY -> resources.getString(R.string.july_short)
        java.time.Month.AUGUST -> resources.getString(R.string.august_short)
        java.time.Month.SEPTEMBER -> resources.getString(R.string.september_short)
        java.time.Month.OCTOBER -> resources.getString(R.string.october_short)
        java.time.Month.NOVEMBER -> resources.getString(R.string.november_short)
        java.time.Month.DECEMBER -> resources.getString(R.string.december_short)
    }
}

fun java.time.LocalDate.toKotlin(): LocalDate {
    return LocalDate(this.year, this.monthValue, this.dayOfMonth)
}

fun LocalDate.toJava(): java.time.LocalDate {
    return java.time.LocalDate.of(this.year, this.month, this.dayOfMonth)
}

fun LocalDate.yearMonthToJava(): java.time.YearMonth {
    return java.time.YearMonth.of(this.year, this.month)
}