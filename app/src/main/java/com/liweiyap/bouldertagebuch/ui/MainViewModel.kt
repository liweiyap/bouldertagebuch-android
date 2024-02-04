package com.liweiyap.bouldertagebuch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.MainRepository
import com.liweiyap.bouldertagebuch.utils.getDate
import com.liweiyap.bouldertagebuch.utils.mutableStateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository,
): ViewModel() {
    private val _userDefinedGym: MutableStateFlow<Gym?> = repo.getUserDefinedGym().mutableStateIn(scope = viewModelScope, initialValue = null)
    val userDefinedGym = _userDefinedGym.asStateFlow()

    private val _todayGymId: MutableStateFlow<GymId> = repo.getTodayGymId().mutableStateIn(scope = viewModelScope, initialValue = GymId.UNKNOWN)
    val todayGymId = _todayGymId.asStateFlow()

    private val _todayRouteCount: MutableStateFlow<List<Int>> = repo.getTodayRouteCount().mutableStateIn(scope = viewModelScope, initialValue = listOf())
    val todayRouteCount = _todayRouteCount.asStateFlow()

    private val _paginatedLog: MutableStateFlow<Map<LocalDate, Pair<GymId, List<Int>>>> = repo.getPaginatedLog().mutableStateIn(scope = viewModelScope, initialValue = mapOf())
    val paginatedLog = _paginatedLog.asStateFlow()

    private val _years: MutableStateFlow<List<Int>> = repo.getYears().mutableStateIn(scope = viewModelScope, initialValue = listOf())
    val years = _years.asStateFlow()

    private val _viewedYear: MutableStateFlow<Int> = repo.getViewedYear().mutableStateIn(scope = viewModelScope, initialValue = getDate().year)
    val viewedYear = _viewedYear.asStateFlow()

    fun setTodayGymId(id: GymId) = viewModelScope.launch {
        repo.setTodayGymId(id)
    }

    fun increaseTodayRouteCount(index: Int) = viewModelScope.launch {
        repo.increaseTodayRouteCount(index)
    }

    fun decreaseTodayRouteCount(index: Int) = viewModelScope.launch {
        repo.decreaseTodayRouteCount(index)
    }

    fun clearTodayRouteCount() = viewModelScope.launch {
        repo.clearTodayRouteCount()
    }

    fun setViewedYear(year: Int) = viewModelScope.launch {
        repo.setViewedYear(year)
    }
}