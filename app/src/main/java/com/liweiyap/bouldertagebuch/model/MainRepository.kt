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

    /*
    suspend fun setUpScreenshots() {
        context.dataStore.updateData { userPrefs: UserPreferences ->
            userPrefs.copy(
                log = userPrefs.log.mutate { map ->
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 2)]  = Pair(gymVels.id,     listOf(0,0,1,6,2,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 6)]  = Pair(gymRockerei.id, listOf(0,2,2,2,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 13)] = Pair(gymRockerei.id, listOf(0,1,3,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 17)] = Pair(gymVels.id,     listOf(0,0,0,4,1,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 20)] = Pair(gymRockerei.id, listOf(0,1,2,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 27)] = Pair(gymRockerei.id, listOf(0,0,2,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 6, dayOfMonth = 30)] = Pair(gymVels.id,     listOf(0,0,0,3,2,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 4)]  = Pair(gymRockerei.id, listOf(0,2,4,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 10)] = Pair(gymVels.id,     listOf(0,2,2,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 14)] = Pair(gymRockerei.id, listOf(0,1,2,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 17)] = Pair(gymVels.id,     listOf(0,1,1,3,2,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 21)] = Pair(gymRockerei.id, listOf(0,0,6,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 28)] = Pair(gymRockerei.id, listOf(0,0,4,2,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 1)]  = Pair(gymRockerei.id, listOf(0,0,3,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 5)]  = Pair(gymVels.id,     listOf(0,0,0,3,3,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 22)] = Pair(gymRockerei.id, listOf(0,1,2,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 25)] = Pair(gymRockerei.id, listOf(0,0,3,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 29)] = Pair(gymRockerei.id, listOf(0,0,2,5,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 2)]  = Pair(gymVels.id,     listOf(0,0,1,2,3,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 5)]  = Pair(gymRockerei.id, listOf(0,0,4,6,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 19)] = Pair(gymRockerei.id, listOf(0,2,4,3,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 23)] = Pair(gymVels.id,     listOf(0,0,0,2,4,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 29)] = Pair(gymRockerei.id, listOf(0,0,4,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 10, dayOfMonth = 3)] = Pair(gymRockerei.id, listOf(0,0,3,4,0,0,0,0))
                    map[LocalDate(year = 2024, monthNumber = 10, dayOfMonth = 6)] = Pair(gymVels.id,     listOf(0,0,0,2,4,0,0,0))
                }
            )
        }
    }
    */
}