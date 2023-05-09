package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.model.HabitDB
import com.masharo.habitstrackercompose.ui.screen.habit.Type

enum class Page(
    @StringRes val title: Int
) {
    POSITIVE_HABIT_LIST(
        title = R.string.page_positive
    ) {
        override fun filter(
            habit: HabitDB
        ) = Type.values()[habit.type] == Type.POSITIVE
    },
    NEGATIVE_HABIT_LIST(
        title = R.string.page_negative
    ) {
        override fun filter(
            habit: HabitDB
        ) = Type.values()[habit.type] == Type.NEGATIVE
    };

    abstract fun filter(
        habit: HabitDB
    ): Boolean
}