package com.liweiyap.bouldertagebuch.model

import kotlinx.serialization.Serializable

@Serializable
data class Difficulty(
    val level: Int,
    val colorName: String,
    val grade: String = "",
)
