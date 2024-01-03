package com.liweiyap.bouldertagebuch.model

import android.content.Context
import androidx.datastore.dataStore
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferences
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferencesSerializer
import com.liweiyap.bouldertagebuch.utils.getDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Collections
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "user-prefs.json", serializer = UserPreferencesSerializer)

class MainRepository @Inject constructor(
    private val context: Context,
) {
    fun getUserDefinedGym(): Flow<Gym?> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.gym2
        }
    }

    suspend fun setTodayGymId(id: GymId) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.put(getDate(), Pair(id, initTodayRouteList(id)))
            )
        }
    }

    fun getTodayGymId(): Flow<GymId> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.log[getDate()]?.first ?: GymId.UNKNOWN
        }
    }

    suspend fun setTodayRouteCount(count: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.put(getDate(), Pair(GymId.UNKNOWN, arrayListOf(count)))
            )
        }
    }

    fun getTodayRouteCount(): Flow<ArrayList<Int>> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.log[getDate()]?.second ?: arrayListOf()
        }
    }

    companion object {
        private fun initTodayRouteList(gymId: GymId): ArrayList<Int> {
            val size: Int = when (gymId) {
                gymRockerei.id -> gymRockerei.getDifficultiesSortedByLevel().size
                gymVels.id -> gymVels.getDifficultiesSortedByLevel().size
                else -> 0
            }

            return ArrayList(Collections.nCopies(size, 0))
        }
    }
}