package com.liweiyap.bouldertagebuch.model.datastore

import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Created to use Proto DataStore without protobuf files,
 * because in .proto files, field numbers must be positive integers, i.e. cannot be 0 (or negative)
 */
@Serializable
data class UserPreferences(
    val gym0: Gym = gymRockerei,
    val gym1: Gym = gymVels,
    val gym2: Gym? = null,

    @Serializable(with = PersistentMapSerializer::class)
    val log: PersistentMap<LocalDate, Pair<GymId, ArrayList<Int>>> = persistentMapOf(),
)