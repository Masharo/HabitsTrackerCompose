package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update

val habitsFlow = MutableStateFlow(
    listOf<Habit>(
        Habit(
            _id = 0,
            title = "positive",
            description = "123",
            count = "12",
            period = "day"
        ),
        Habit(
            _id = 1,
            title = "negative",
            description = "123",
            type = Type.NEGATIVE,
            count = "12",
            period = "day"
        )
    )
)

fun updateHabit(
    id: Long,
    habit: Habit
) {
    habitsFlow.update { habits ->
        val newHabits = mutableListOf(*habits.toTypedArray())
        newHabits.filter { it._id == id }.map { habit }
    }
}

fun addHabit(
    habit: Habit
) {
    habitsFlow.update { habits ->
        mutableListOf(*habits.toTypedArray(), habit.copy(_id = id))
    }
    id++
}

fun getHabitById(id: Long) = habitsFlow.value.first { it._id == id }

private var id = 2L