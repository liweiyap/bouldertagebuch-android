package com.liweiyap.bouldertagebuch

import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.dialogs.canCalculateBeginnerLevelOffset
import com.liweiyap.bouldertagebuch.ui.dialogs.canCalculateExpertLevelOffset
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test

import org.junit.Assert.*

class TestDialogDifficultySelectLevelOffsets {
    @Test
    fun testCalculateGym0LevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymTest0.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 1)

        val index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateGym1LevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymTest1.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 1)

        val index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateGym2LevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymTest2.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 2)

        var index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 1
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateGym3LevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymTest3.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 3)

        var index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 1
        assertTrue(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 2
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertTrue(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateGym4LevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymTest4.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 2)

        var index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 1
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateRockereiLevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymRockerei.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 8)

        var index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 1
        assertTrue(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 2
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 3
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 4
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 5
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 6
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 7
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertTrue(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    @Test
    fun testCalculateVelsLevelOffsets() {
        val levels: ArrayList<ArrayList<Difficulty>> = gymVels.getDifficultiesSortedByLevel()
        assertEquals(levels.size, 8)

        var index = 0
        val levelCount: Int = levels.size
        val isFirstLevelNegative: Boolean = (levels[0].first().level < 0)
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 1
        assertTrue(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 2
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 3
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 4
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 5
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 6
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertFalse(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))

        index = 7
        assertFalse(canCalculateBeginnerLevelOffset(index, levelCount, isFirstLevelNegative))
        assertTrue(canCalculateExpertLevelOffset(index, levelCount, isFirstLevelNegative))
    }

    private val gymTest0: Gym = Gym(
        id = GymId.UNKNOWN,
        name = "Test 0",
        difficulties = persistentListOf(
            Difficulty(level = 0, colorName = "green"),
        ),
    )

    private val gymTest1: Gym = Gym(
        id = GymId.UNKNOWN,
        name = "Test 1",
        difficulties = persistentListOf(
            Difficulty(level = -1, colorName = "turquoise"),
        ),
    )

    private val gymTest2: Gym = Gym(
        id = GymId.UNKNOWN,
        name = "Test 2",
        difficulties = persistentListOf(
            Difficulty(level = -1, colorName = "turquoise"),
            Difficulty(level = 0, colorName = "green"),
        ),
    )

    private val gymTest3: Gym = Gym(
        id = GymId.UNKNOWN,
        name = "Test 3",
        difficulties = persistentListOf(
            Difficulty(level = -1, colorName = "turquoise"),
            Difficulty(level = 0, colorName = "green"),
            Difficulty(level = 1, colorName = "yellow"),
        ),
    )

    private val gymTest4: Gym = Gym(
        id = GymId.UNKNOWN,
        name = "Test 3",
        difficulties = persistentListOf(
            Difficulty(level = -1, colorName = "turquoise"),
            Difficulty(level = 0, colorName = "green"),
            Difficulty(level = 0, colorName = "yellow"),
        ),
    )
}