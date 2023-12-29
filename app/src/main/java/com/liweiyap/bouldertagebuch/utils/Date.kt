package com.liweiyap.bouldertagebuch.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun getDate(): LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}