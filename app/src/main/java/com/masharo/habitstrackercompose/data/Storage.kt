package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val habitsFlow = MutableStateFlow(
    listOf<Habit>()
)

fun updateHabit(
    id: Long,
    habit: Habit
) {
    habitsFlow.update { currentHabits ->
        currentHabits.map { currentHabit ->
            if (currentHabit.id == id) habit.copy(id = id)
            else currentHabit
        }
    }
}

fun addHabit(
    habit: Habit
) {
    habitsFlow.update { habits ->
        mutableListOf(*habits.toTypedArray(), habit.copy(id = id))
    }
    id++
}

fun getHabitById(id: Long) = habitsFlow.value.first { it.id == id }

private var id = 2L