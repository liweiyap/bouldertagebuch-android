package com.liweiyap.bouldertagebuch

import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.PaginatedLog
import com.liweiyap.bouldertagebuch.ui.screens.getHighlightedDateByGym
import com.liweiyap.bouldertagebuch.utils.toKotlin
import kotlinx.datetime.LocalDate
import org.junit.Assert
import org.junit.Test

class TestHighlightedDate {
    @Test
    fun testMap0HighlightedDate() {
        Assert.assertEquals(getHighlightedDateByGym(map0, GymId.ROCKEREI)?.toKotlin(), LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2))
        Assert.assertNull(getHighlightedDateByGym(map0, GymId.VELS))
    }

    @Test
    fun testMap1HighlightedDate() {
        Assert.assertEquals(getHighlightedDateByGym(map1, GymId.ROCKEREI)?.toKotlin(), LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1))
        Assert.assertNull(getHighlightedDateByGym(map1, GymId.VELS))
    }

    @Test
    fun testMap2HighlightedDate() {
        Assert.assertEquals(getHighlightedDateByGym(map2, GymId.ROCKEREI)?.toKotlin(), LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3))
        Assert.assertNull(getHighlightedDateByGym(map2, GymId.VELS))
    }

    @Test
    fun testMap3HighlightedDate() {
        Assert.assertNull(getHighlightedDateByGym(map3, GymId.ROCKEREI))
        Assert.assertNull(getHighlightedDateByGym(map3, GymId.VELS))
    }

    @Test
    fun testMap4HighlightedDate() {
        Assert.assertEquals(getHighlightedDateByGym(map4, GymId.ROCKEREI)?.toKotlin(), LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2))
        Assert.assertNull(getHighlightedDateByGym(map4, GymId.VELS))
    }

    @Test
    fun testMap5HighlightedDate() {
        Assert.assertEquals(getHighlightedDateByGym(map5, GymId.ROCKEREI)?.toKotlin(), LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2))
        Assert.assertNull(getHighlightedDateByGym(map5, GymId.VELS))
    }

    @Test
    fun testMap6HighlightedDate() {
        Assert.assertNull(getHighlightedDateByGym(map6, GymId.ROCKEREI))
        Assert.assertNull(getHighlightedDateByGym(map6, GymId.VELS))
    }

    private val map0: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 3, 5, 1, 0, 0, 0, 0)),
    ))

    private val map1: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 3, 5, 1, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 0, 0, 0, 0, 0)),
    ))

    private val map2: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 3, 5, 1, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 2, 0, 0, 0, 0)),
    ))

    private val map3: PaginatedLog = PaginatedLog(emptyMap())

    private val map4: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 2, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3) to Pair(GymId.ROCKEREI, listOf(0, 3, 5, 1, 0, 0, 0, 0)),
    ))

    private val map5: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 1, 12, 2, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3) to Pair(GymId.ROCKEREI, listOf(0, 3, 5, 1, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3) to Pair(GymId.ROCKEREI, listOf(14, 0, 0, 0, 0, 0, 0, 0)),
    ))

    private val map6: PaginatedLog = PaginatedLog(mapOf(
        LocalDate(year = 2024, monthNumber = 1, dayOfMonth = 1) to Pair(GymId.ROCKEREI, listOf(0, 0, 0, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 2, dayOfMonth = 2) to Pair(GymId.ROCKEREI, listOf(0, 0, 0, 0, 0, 0, 0, 0)),
        LocalDate(year = 2024, monthNumber = 3, dayOfMonth = 3) to Pair(GymId.ROCKEREI, listOf(0, 0, 0, 0, 0, 0, 0, 0)),
    ))
}