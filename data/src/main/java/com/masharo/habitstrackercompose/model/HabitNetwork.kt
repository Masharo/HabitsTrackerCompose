package com.masharo.habitstrackercompose.model

import androidx.annotation.IntRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitNetwork(
    val color: Int? = null,
    val count: Int,
    val date: Int,
    val description: String,
    @SerialName("done_dates") val doneDate: List<Int>,
    val frequency: Int,
    @IntRange(from = 0, to = 2)
    val priority: Int,
    val title: String,
    @IntRange(from = 0, to = 1)
    val type: Int,
    val uid: String = ""
)

fun HabitNetwork.toHabitDB() = HabitDB(
    uid         = uid,
    title       = title,
    description = description,
    priority    = priority,
    type        = type,
    count       = count,
    countReady  = doneDate.size,
    period      = frequency,
    color       = color
)

fun List<HabitNetwork>.toHabitsDB() = map { habitNetwork ->
    habitNetwork.toHabitDB()
}
