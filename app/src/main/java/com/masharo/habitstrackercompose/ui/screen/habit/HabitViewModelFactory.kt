package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitViewModelFactory(
    private val idHabit: Int?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HabitViewModel(
            idHabit = idHabit
        ) as T
}