package com.liweiyap.bouldertagebuch.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

// see: https://skyyo.medium.com/performance-in-jetpack-compose-9a85ce02f8f9
@Immutable
class PaginatedLog(val entries: Map<LocalDate, Pair<GymId, List<Int>>>)