package com.masharo.habitstrackercompose.model

data class Habit(
    var id: Long = 0,
    val uid: String = "",
    val title: String,
    val description: String,
    val priority: Int,
    val type: HabitType,
    val count: Int,
    val period: Int,
    var countReady: Int,
    val color: Int?
)
