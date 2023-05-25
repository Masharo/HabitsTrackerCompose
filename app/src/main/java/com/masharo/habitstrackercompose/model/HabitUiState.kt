package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.masharo.habitstrackercompose.ui.screen.habit.Period
import com.masharo.habitstrackercompose.ui.screen.habit.Priority
import com.masharo.habitstrackercompose.ui.screen.habit.Type
import com.masharo.habitstrackercompose.ui.screen.habitsList.ColumnSort
import com.masharo.habitstrackercompose.ui.screen.habitsList.Page
import com.masharo.habitstrackercompose.ui.screen.habitsList.TypeSort

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

fun HabitDB.toHabitUiState() = HabitUiState(
    title = title,
    description = description,
    priority = Priority.values()[priority],
    type = Type.values()[type],
    count = count.toString(),
    countReady = countReady.toString(),
    period = Period.values()[period],
    color = color?.let { Color(it) },

    isTitleError = false,
    isDescriptionError = false,
    isCountError = false,
    isCountReadyError = false,

    isError = false
)

fun HabitDB.toHabitListItemUiState() = HabitListItemUiState(
    id = id,
    title = title,
    _description = description,
    priority = Priority.values()[priority],
    type = Type.values()[type],
    count = count.toString(),
    countReady = countReady.toString(),
    period = Period.values()[period],
    color = color?.let { Color(it) }
)

fun List<HabitDB>.toHabitListItemUiState(
    filter: (HabitDB) -> Boolean
) = this.filter { filter(it) }.map { it.toHabitListItemUiState() }

fun List<HabitDB>.toHabitListUiState(
    pages: Iterable<Int>,
    countPage: Int,
    search: String,
    isAsc: Boolean,
    columnSort: ColumnSort
) = HabitListUiState(
    pages = pages,
    countPage = countPage,
    search = search,
    typeSort = TypeSort.getTypeSort(isAsc),
    columnSort = columnSort,
    habitsPositive = this.filter { Page.POSITIVE_HABIT_LIST.filter(it) }.map { it.toHabitListItemUiState() },
    habitsNegative = this.filter { Page.NEGATIVE_HABIT_LIST.filter(it) }.map { it.toHabitListItemUiState() }
)