package com.liweiyap.bouldertagebuch.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class Gym(
    val id: GymId,
    val name: String,
    val routes: PersistentList<Difficulty>  /* immutable */,
)

enum class GymId {
    ROCKEREI,
    VELS,
    USERDEFINED0,
    UNKNOWN
}

val gymRockerei: Gym = Gym(
    id = GymId.ROCKEREI,
    name = "rockerei, Stuttgart",
    routes = persistentListOf(
        Difficulty(level = -1, colorName = "turquoise"),
        Difficulty(level = 0, colorName = "green", grade = "2a - 3c"),
        Difficulty(level = 1, colorName = "purple", grade = "3b - 4c"),
        Difficulty(level = 2, colorName = "blue", grade = "4b - 5c"),
        Difficulty(level = 3, colorName = "black", grade = "5b - 6b"),
        Difficulty(level = 4, colorName = "orange", grade = "6a - 6c"),
        Difficulty(level = 5, colorName = "red", grade = "6c - 7b"),
        Difficulty(level = 6, colorName = "yellow", grade = "7a - 8a"),
    ),
)

val gymVels: Gym = Gym(
    id = GymId.VELS,
    name = "VELS, Stuttgart",
    routes = persistentListOf(
        Difficulty(level = -1, colorName = "turquoise"),
        Difficulty(level = 0, colorName = "grey"),
        Difficulty(level = 1, colorName = "yellow"),
        Difficulty(level = 2, colorName = "green"),
        Difficulty(level = 3, colorName = "purple"),
        Difficulty(level = 3, colorName = "pink"),
        Difficulty(level = 4, colorName = "black"),
        Difficulty(level = 4, colorName = "blue"),
        Difficulty(level = 5, colorName = "red"),
        Difficulty(level = 5, colorName = "orange"),
        Difficulty(level = 6, colorName = "white"),
    ),
)