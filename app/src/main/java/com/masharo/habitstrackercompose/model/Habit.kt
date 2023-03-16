package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color

data class Habit(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MIDDLE,
    val type: Type = Type.POSITIVE,
    val count: String = "",
    val countReady: String = "0",
    val period: String = "",
    val color: Color? = null
)

fun Habit.toHabitUiState() = HabitUiState(
    title = title,
    description = description,
    priority = priority,
    type = type,
    count = count,
    countReady = countReady,
    period = period,
    color = color,

    isTitleError = false,
    isDescriptionError = false,
    isCountError = false,
    isCountReadyError = false,
    isPeriodError = false,

    isError = false
)

fun Habit.toHabitListItemUiState() = HabitListItemUiState(
    title = title,
    description = "${description.substring(0, 60)}${if (description.length > 60) "..." else ""}",
    priority = priority,
    type = type,
    count = count,
    countReady = countReady,
    period = period,
    color = color
)

fun List<Habit>.toHabitListUiState() = HabitListUiState(
    habits = map { it.toHabitListItemUiState() }
)