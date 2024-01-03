package com.liweiyap.bouldertagebuch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.MainRepository
import com.liweiyap.bouldertagebuch.utils.mutableStateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository,
): ViewModel() {
    private val _userDefinedGym: MutableStateFlow<Gym?> = repo.getUserDefinedGym().mutableStateIn(scope = viewModelScope, initialValue = null)
    val userDefinedGym = _userDefinedGym.asStateFlow()

    private val _todayGymId: MutableStateFlow<GymId> = repo.getTodayGymId().mutableStateIn(scope = viewModelScope, initialValue = GymId.UNKNOWN)
    val todayGymId = _todayGymId.asStateFlow()

    private val _todayRouteCount: MutableStateFlow<ArrayList<Int>> = repo.getTodayRouteCount().mutableStateIn(scope = viewModelScope, initialValue = arrayListOf())
    val todayRouteCount = _todayRouteCount.asStateFlow()

    fun setTodayGymId(id: GymId) = viewModelScope.launch {
        repo.setTodayGymId(id)
    }
}