package com.liweiyap.bouldertagebuch.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
class PaginatedLog(val entries: Map<LocalDate, Pair<GymId, List<Int>>>)