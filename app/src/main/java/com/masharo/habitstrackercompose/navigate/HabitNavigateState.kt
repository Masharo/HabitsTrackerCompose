package com.masharo.habitstrackercompose.navigate

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class HabitNavigateState(
    @StringRes val screenTitle: Int? = null,
    val isHaveFabAddHabit: Boolean = false,
    val isHaveTopAppBar: Boolean = true
) {
    Home(
        screenTitle = R.string.app_bar_name_screen_list_habits,
        isHaveFabAddHabit = true
    ),
    AddNewHabit(
        screenTitle = R.string.app_bar_name_screen_new_habit_add
    ),
    UpdateHabit(
        screenTitle = R.string.app_bar_name_screen_habit_update
    ),
    ApplicationInfo(
        screenTitle = R.string.application_info_title
    )
}

//Убираем параметры у строки и пытаемся сопоставить с HabitNavigateState
fun habitNavigateState(state: String?) =
    HabitNavigateState.valueOf(
        value = state?.replace(Regex("""/.*"""), "")
                ?:
                HabitNavigateState.Home.name
    )