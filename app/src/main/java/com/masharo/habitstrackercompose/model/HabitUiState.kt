package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.masharo.habitstrackercompose.model.HabitDB
import com.masharo.habitstrackercompose.ui.screen.habit.Period
import com.masharo.habitstrackercompose.ui.screen.habit.Priority
import com.masharo.habitstrackercompose.ui.screen.habit.Type

data class HabitUiState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MIDDLE,
    val type: Type = Type.POSITIVE,
    val count: String = "",
    val countReady: String = "0",
    val period: Period = Period.WEEK,
    val color: Color? = null,

    val isTitleError: Boolean = false,
    val isDescriptionError: Boolean = false,
    val isCountError: Boolean = false,
    val isCountReadyError: Boolean = false,

    val isError: Boolean = false,

    val isNavigateToHabitsList: Boolean = false
)

fun HabitUiState.toHabit(uid: String = "") = com.masharo.habitstrackercompose.model.HabitDB(
    id = 0,
    uid = uid,
    title = title,
    description = description,
    priority = Priority.values().indexOf(priority),
    type = Type.values().indexOf(type),
    count = count.toInt(),
    countReady = countReady.toInt(),
    period = Period.values().indexOf(period),
    color = color?.toArgb()
)

fun HabitUiState.toHabit(id: Long, uid: String = "") =
    com.masharo.habitstrackercompose.model.HabitDB(
        id = id,
        uid = uid,
        title = title,
        description = description,
        priority = Priority.values().indexOf(priority),
        type = Type.values().indexOf(type),
        count = count.toInt(),
        countReady = countReady.toInt(),
        period = Period.values().indexOf(period),
        color = color?.toArgb()
    )