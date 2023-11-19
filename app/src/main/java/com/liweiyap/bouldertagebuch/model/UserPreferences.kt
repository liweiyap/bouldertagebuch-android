package com.liweiyap.bouldertagebuch.model

import kotlinx.serialization.Serializable

/**
 * Created to use Proto DataStore without protobuf files,
 * because in .proto files, field numbers must be positive integers, i.e. cannot be 0 (or negative)
 */
@Serializable
data class UserPreferences(
    val todayRouteCount: Int = 0,
)