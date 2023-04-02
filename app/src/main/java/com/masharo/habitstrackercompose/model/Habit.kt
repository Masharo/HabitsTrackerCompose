package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_habit")      val id: Long = 0,
    @ColumnInfo(name = "title")         val title: String = "",
    @ColumnInfo(name = "description")   val description: String = "",
    @ColumnInfo(name = "priority")      val priority: Priority = Priority.MIDDLE,
    @ColumnInfo(name = "type")          val type: Type = Type.POSITIVE,
    @ColumnInfo(name = "count")         val count: String = "",
    @ColumnInfo(name = "count_ready")   val countReady: String = "0",
    @ColumnInfo(name = "period")        val period: String = "",
    @ColumnInfo(name = "color")         val color: Long? = null
)

fun Habit.toHabitUiState() = HabitUiState(
    title = title,
    description = description,
    priority = priority,
    type = type,
    count = count,
    countReady = countReady,
    period = period,
    color = color?.let { Color(it) },

    isTitleError = false,
    isDescriptionError = false,
    isCountError = false,
    isCountReadyError = false,
    isPeriodError = false,

    isError = false
)

fun Habit.toHabitListItemUiState() = HabitListItemUiState(
    id = id,
    title = title,
    _description = description,
    priority = priority,
    type = type,
    count = count,
    countReady = countReady,
    period = period,
    color = color?.let { Color(it) }
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