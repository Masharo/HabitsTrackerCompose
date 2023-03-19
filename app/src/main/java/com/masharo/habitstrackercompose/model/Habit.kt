package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color
import com.masharo.habitstrackercompose.ui.screen.habitsList.Page

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
    _description = description,
    priority = priority,
    type = type,
    count = count,
    countReady = countReady,
    period = period,
    color = color
)

fun List<Habit>.toHabitListItemUiState(
    filter: (Habit) -> Boolean
) = this.filter { filter(it) }.map { it.toHabitListItemUiState() }

fun List<Habit>.toHabitListUiState(
    pages: Iterable<Int>,
    countPage: Int
) = HabitListUiState(
    pages = pages,
    countPage = countPage,
    habitsPositive = this.filter { Page.POSITIVE_HABIT_LIST.filter(it) }.map { it.toHabitListItemUiState() },
    habitsNegative = this.filter { Page.NEGATIVE_HABIT_LIST.filter(it) }.map { it.toHabitListItemUiState() }
)