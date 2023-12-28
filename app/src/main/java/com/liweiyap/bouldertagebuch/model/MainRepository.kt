package com.liweiyap.bouldertagebuch.model

import android.content.Context
import androidx.datastore.dataStore
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferences
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferencesSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "user-prefs.json", serializer = UserPreferencesSerializer)

class MainRepository @Inject constructor(
    private val context: Context,
) {
    suspend fun setTodayRouteCount(count: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.put(getDate(), Pair(GymId.UNKNOWN, arrayListOf(count)))
            )
        }
    }

    fun getTodayRouteCount(): Flow<Int> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.log[getDate()]?.second?.sum() ?: 0
        }
    }

    private fun getDate(): LocalDate {
        return Clock.System.todayIn(TimeZone.currentSystemDefault())
    }
}