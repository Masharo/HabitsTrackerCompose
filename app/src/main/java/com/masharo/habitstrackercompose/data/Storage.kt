package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val habitsFlow = MutableStateFlow(
    mutableListOf<Habit>()
)

fun updateHabit(
    index: Int,
    habit: Habit
) {
    habitsFlow.update { habits ->
        val newHabits = mutableListOf(*habits.toTypedArray())
        newHabits[index] = habit
        newHabits
    }
}

fun addHabit(
    habit: Habit
) {
    habitsFlow.update { habits ->
        mutableListOf(*habits.toTypedArray(), habit)
    }
}