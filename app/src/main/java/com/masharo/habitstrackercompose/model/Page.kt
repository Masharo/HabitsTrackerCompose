package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class Page(
    @StringRes val title: Int
) {
    POSITIVE_HABIT_LIST(
        title = R.string.page_positive
    ) {
        override fun filter(
            habit: Habit
        ) = habit.type == Type.POSITIVE
    },
    NEGATIVE_HABIT_LIST(
        title = R.string.page_negative
    ) {
        override fun filter(
            habit: Habit
        ) = habit.type == Type.NEGATIVE
    };

    abstract fun filter(
        habit: Habit
    ): Boolean
}