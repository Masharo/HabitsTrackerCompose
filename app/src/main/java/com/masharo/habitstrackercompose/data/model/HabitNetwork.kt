package com.masharo.habitstrackercompose.data.model

import androidx.annotation.IntRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitNetwork(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    @SerialName("done_date") val doneDate: List<Int>,
    val frequency: Int,
    @IntRange(from = 0, to = 2)
    val priority: Int,
    val title: String,
    @IntRange(from = 0, to = 1)
    val type: Int,
    val uid: String
)
