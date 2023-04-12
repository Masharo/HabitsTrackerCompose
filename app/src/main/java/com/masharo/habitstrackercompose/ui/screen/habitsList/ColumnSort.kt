package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.HabitRepository
import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

enum class ColumnSort(
    @StringRes val title: Int,
    @StringRes val selectedTitle: Int
) {

    PRIORITY(
        title = R.string.column_priority_variant,
        selectedTitle = R.string.column_priority_selected
    ) {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): LiveData<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByPriority(
            title = search,
            isAsc = isAsc
        )

    },

    COUNT(
        title = R.string.column_count_variant,
        selectedTitle = R.string.column_count_selected
    ) {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): LiveData<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByCount(
            title = search,
            isAsc = isAsc
        )

    },

    ID(
        title = R.string.column_id_variant,
        selectedTitle = R.string.column_id_selected
    ) {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): LiveData<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderById(
            title = search,
            isAsc = isAsc
        )

    };

    abstract fun getHabits(
        habitRepository: HabitRepository,
        search: String,
        isAsc: Boolean
    ): LiveData<List<Habit>>

    companion object {

        fun defaultValue() = ID

    }
}