package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habitstrackercompose.model.Habit

class HabitListViewModelFactory(
    val habits: List<Habit>
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        HabitListViewModel(
            habits = habits
        ) as T
}