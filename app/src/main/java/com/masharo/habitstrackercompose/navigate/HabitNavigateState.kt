package com.masharo.habitstrackercompose.navigate

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class HabitNavigateState(
    @StringRes val screenTitle: Int,
    val isNeedFabAddHabit: Boolean
) {
    Start(
        screenTitle = R.string.app_bar_name_screen_list_habits,
        isNeedFabAddHabit = true
    ),
    AddNewHabit(
        screenTitle = R.string.app_bar_name_screen_new_habit_add,
        isNeedFabAddHabit = false
    ),
    UpdateHabit(
        screenTitle = R.string.app_bar_name_screen_habit_update,
        isNeedFabAddHabit = false
    )
}

//Убираем параметры у строки и пытаемся сопоставить с HabitNavigateState
fun habitNavigateState(state: String?) =
    HabitNavigateState.valueOf(
        value = state?.replace(Regex("""/.*"""), "")
                ?:
                HabitNavigateState.Start.name
    )