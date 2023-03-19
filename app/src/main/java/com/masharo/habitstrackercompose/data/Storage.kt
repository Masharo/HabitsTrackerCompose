package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val habitsFlow = MutableStateFlow(
    mutableListOf<Habit>(
        Habit(
            title = "positive",
            description = "123",
            count = "12",
            period = "day"
        ),
        Habit(
            title = "negative",
            description = "123",
            type = Type.NEGATIVE,
            count = "12",
            period = "day"
        )
    )
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