package com.liweiyap.bouldertagebuch.ui.routes

import com.liweiyap.bouldertagebuch.model.Difficulty
import com.liweiyap.bouldertagebuch.model.Gym

fun sortDifficultiesByLevel(gym: Gym): ArrayList<ArrayList<Difficulty>> {
    val levels: ArrayList<ArrayList<Difficulty>> = arrayListOf()

    for (difficulty in gym.difficulties) {
        if (levels.isEmpty()) {
            levels.add(arrayListOf(difficulty))
            continue
        }

        if (levels.last().last().level == difficulty.level) {
            levels.last().add(difficulty)
            continue
        }

        levels.add(arrayListOf(difficulty))
    }

    return levels
}