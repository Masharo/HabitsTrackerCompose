package com.masharo.habitstrackercompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @ColumnInfo(name = "period")        val period: Int = 1,
    @ColumnInfo(name = "color")         val color: Int? = null
)

fun HabitDB.toHabitNetwork() = HabitNetwork(
    uid = uid,
    color = color,
    count = count,
    date = date,
    description = description,
    doneDate = IntArray(countReady) { 1 }.toList(),
    frequency = period,
    priority = priority,
    title = title,
    type = type
)

fun Habit.toHabitDB() = HabitDB(
    id = id,
    uid = uid,
    title = title,
    description = description,
    priority = priority,
    type = HabitType.values().indexOf(type),
    count = count,
    countReady = countReady,
    period = period,
    color = color
)

fun HabitDB.toHabit() = Habit(
    id = id,
    uid = uid,
    title = title,
    description = description,
    priority = priority,
    type = HabitType.values()[type],
    count = count,
    countReady = countReady,
    period = period,
    color = color
)
