package com.masharo.habitstrackercompose.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import com.masharo.habitstrackercompose.model.HabitListUiState
import com.masharo.habitstrackercompose.model.HabitUiState
import com.masharo.habitstrackercompose.ui.screen.habit.Priority
import com.masharo.habitstrackercompose.ui.screen.habit.Type
import com.masharo.habitstrackercompose.ui.screen.habitsList.ColumnSort
import com.masharo.habitstrackercompose.ui.screen.habitsList.Page
import com.masharo.habitstrackercompose.ui.screen.habitsList.TypeSort
import java.util.Calendar

@Entity(tableName = "habits")
data class HabitDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_habit")      val id: Long = 0,
    @ColumnInfo(name = "uid")           val uid: String = "",
    @ColumnInfo(name = "title")         val title: String = "",
    @ColumnInfo(name = "description")   val description: String = "",
    @ColumnInfo(name = "date")          val date: Int = (Calendar.getInstance().timeInMillis / 1000).toInt(),
    @ColumnInfo(name = "priority")      val priority: Int = 1,
    @ColumnInfo(name = "type")          val type: Int = 0,
    @ColumnInfo(name = "count")         val count: Int = 0,
    @ColumnInfo(name = "count_ready")   val countReady: Int = 0,
    @ColumnInfo(name = "period")        val period: String = "",
    @ColumnInfo(name = "color")         val color: Int? = null
)

fun HabitDB.toHabitUiState() = HabitUiState(
    title = title,
    description = description,
    priority = Priority.values()[priority],
    type = Type.values()[type],
    count = count.toString(),
    countReady = countReady.toString(),
    period = period,
    color = color?.let { Color(it) },

    isTitleError = false,
    isDescriptionError = false,
    isCountError = false,
    isCountReadyError = false,
    isPeriodError = false,

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
    period = period,
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

fun HabitDB.toHabitNetwork() = HabitNetwork(
    uid = uid,
    color = color,
    count = count,
    date = date,
    description = description,
    doneDate = IntArray(countReady) { 1 }.toList(),
    frequency = 1,
    priority = priority,
    title = title,
    type = type
)