package com.liweiyap.bouldertagebuch.model

import android.content.Context
import androidx.datastore.dataStore
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferences
import com.liweiyap.bouldertagebuch.model.datastore.UserPreferencesSerializer
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import java.util.Collections
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "user-prefs.json", serializer = UserPreferencesSerializer)

class MainRepository @Inject constructor(
    private val context: Context,
) {
    suspend fun setCurrentDate(date: LocalDate) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                currentDate = if (date > userPrefs.currentDate)
                    date
                else
                    userPrefs.currentDate
            )
        }
    }

    fun getUserDefinedGym(): Flow<Gym?> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.userDefinedGym0
        }
    }

    suspend fun setTodayGymId(id: GymId) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.put(userPrefs.currentDate, Pair(id, initTodayRouteList(id)))
            )
        }
    }

    fun getTodayGymId(): Flow<GymId> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.log[userPrefs.currentDate]?.first ?: GymId.UNKNOWN
        }
    }

    suspend fun increaseTodayRouteCount(index: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.mutate { map ->
                    val today: LocalDate = userPrefs.currentDate
                    val newCount = ArrayList(map[today]!!.second)
                    ++newCount[index]
                    map[today] = Pair(userPrefs.log[today]!!.first, newCount)
                }
            )
        }
    }

    suspend fun decreaseTodayRouteCount(index: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.mutate { map ->
                    val today: LocalDate = userPrefs.currentDate
                    val newCount = ArrayList(map[today]!!.second)
                    --newCount[index]
                    map[today] = Pair(userPrefs.log[today]!!.first, newCount)
                }
            )
        }
    }

    suspend fun clearTodayRouteCount() {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.remove(userPrefs.currentDate)
            )
        }
    }

    fun getTodayRouteCount(): Flow<List<Int>> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.log[userPrefs.currentDate]?.second ?: listOf()
        }
    }

    fun getPaginatedLog(): Flow<PaginatedLog> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            PaginatedLog(userPrefs.log.filter {
                it.key.year == userPrefs.viewedYear
            })
        }
    }

    fun getYears(): Flow<List<Int>> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            val years: ArrayList<Int> = arrayListOf()
            val today: LocalDate = userPrefs.currentDate

            val iterator: Iterator<Map.Entry<LocalDate, Pair<GymId, List<Int>>>> = userPrefs.log.entries.iterator()
            if (iterator.hasNext()) {
                val firstEntryYear: Int = iterator.next().key.year
                years.add(firstEntryYear)

                for (year in (firstEntryYear + 1) .. today.year) {
                    years.add(year)
                }
            }
            else {
                years.add(today.year)
            }

            years.reversed()
        }
    }

    suspend fun setViewedYear(year: Int) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                viewedYear = year
            )
        }
    }

    fun getViewedYear(): Flow<Int> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.viewedYear
        }
    }

    suspend fun setViewedHighlightedGymId(gymId: GymId) {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                viewedHighlightedGymId = gymId
            )
        }
    }

    fun getViewedHighlightedGymId(): Flow<GymId> {
        return context.dataStore.data.map { userPrefs: UserPreferences ->
            userPrefs.viewedHighlightedGymId
        }
    }

    companion object {
        private fun initTodayRouteList(gymId: GymId): List<Int> {
            val size: Int = when (gymId) {
                gymRockerei.id -> gymRockerei.getDifficultiesSortedByLevel().size
                gymVels.id -> gymVels.getDifficultiesSortedByLevel().size
                else -> 0
            }

            return ArrayList(Collections.nCopies(size, 0))
        }
    }
}