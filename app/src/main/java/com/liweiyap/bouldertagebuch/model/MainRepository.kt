package com.liweiyap.bouldertagebuch.model

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "user-prefs.json", serializer = UserPreferencesSerializer)

class MainRepository @Inject constructor(
    private val context: Context,
) {
    suspend fun setTodayRouteCount(count: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(todayRouteCount = count)
        }
    }

    fun getTodayRouteCount(): Flow<Int> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.todayRouteCount
        }
    }
}