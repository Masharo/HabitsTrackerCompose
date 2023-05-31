package com.masharo.habitstrackercompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitCountDoneNetwork(
    @SerialName("date") val dateVersion: Int,
    @SerialName("habit_uid") val uid: String
)
