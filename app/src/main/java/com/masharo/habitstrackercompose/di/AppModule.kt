package com.masharo.habitstrackercompose.di

import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModel
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HabitListViewModel> {
        HabitListViewModel(
            dbHabitRepository = get(),
            networkHabitRepository = get()
        )
    }

    viewModel<HabitViewModel> { params ->
        HabitViewModel(
            idHabit = params.getOrNull(),
            habitRepository = get()
        )
    }

}