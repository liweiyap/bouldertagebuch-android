package com.liweiyap.bouldertagebuch

import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.dialogs.sortDifficultiesByLevel
import org.junit.Test

import org.junit.Assert.*

class TestSortDifficultiesByLevel {
    @Test
    fun testSortRockereiDifficultiesByLevel() {
        val levels: ArrayList<ArrayList<Difficulty>> = sortDifficultiesByLevel(gymRockerei)
        assertEquals(levels.size, 8)

        assertEquals(levels[0].size, 1)
        assertEquals(levels[0][0], Difficulty(level = -1, colorName = "turquoise"))

        assertEquals(levels[1].size, 1)
        assertEquals(levels[1][0], Difficulty(level = 0, colorName = "green", grade = "2a - 3c"))

        assertEquals(levels[2].size, 1)
        assertEquals(levels[2][0], Difficulty(level = 1, colorName = "purple", grade = "3b - 4c"))

        assertEquals(levels[3].size, 1)
        assertEquals(levels[3][0], Difficulty(level = 2, colorName = "blue", grade = "4b - 5c"))

        assertEquals(levels[4].size, 1)
        assertEquals(levels[4][0], Difficulty(level = 3, colorName = "black", grade = "5b - 6b"))

        assertEquals(levels[5].size, 1)
        assertEquals(levels[5][0], Difficulty(level = 4, colorName = "orange", grade = "6a - 6c"))

        assertEquals(levels[6].size, 1)
        assertEquals(levels[6][0], Difficulty(level = 5, colorName = "red", grade = "6c - 7b"))

        assertEquals(levels[7].size, 1)
        assertEquals(levels[7][0], Difficulty(level = 6, colorName = "yellow", grade = "7a - 8a"))
    }

    @Test
    fun testSortVelsDifficultiesByLevel() {
        val levels: ArrayList<ArrayList<Difficulty>> = sortDifficultiesByLevel(gymVels)
        assertEquals(levels.size, 8)

        assertEquals(levels[0].size, 1)
        assertEquals(levels[0][0], Difficulty(level = -1, colorName = "turquoise"))

        assertEquals(levels[1].size, 1)
        assertEquals(levels[1][0], Difficulty(level = 0, colorName = "grey"))

        assertEquals(levels[2].size, 1)
        assertEquals(levels[2][0], Difficulty(level = 1, colorName = "yellow"))

        assertEquals(levels[3].size, 1)
        assertEquals(levels[3][0], Difficulty(level = 2, colorName = "green"))

        assertEquals(levels[4].size, 2)
        assertEquals(levels[4][0], Difficulty(level = 3, colorName = "purple"))
        assertEquals(levels[4][1], Difficulty(level = 3, colorName = "pink"))

        assertEquals(levels[5].size, 2)
        assertEquals(levels[5][0], Difficulty(level = 4, colorName = "black"))
        assertEquals(levels[5][1], Difficulty(level = 4, colorName = "blue"))

        assertEquals(levels[6].size, 2)
        assertEquals(levels[6][0], Difficulty(level = 5, colorName = "red"))
        assertEquals(levels[6][1], Difficulty(level = 5, colorName = "orange"))

        assertEquals(levels[7].size, 1)
        assertEquals(levels[7][0], Difficulty(level = 6, colorName = "white"))
    }
}