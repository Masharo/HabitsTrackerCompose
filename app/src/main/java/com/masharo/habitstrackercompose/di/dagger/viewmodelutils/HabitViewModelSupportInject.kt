package com.masharo.habitstrackercompose.di.dagger.viewmodelutils

import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModel
import javax.inject.Inject

class HabitViewModelSupportInject(
    viewModelComponent: ViewModelComponent,
    private val idHabit: Long? = null
) {

    init {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var factory: HabitViewModel.Factory

    fun getHabitViewModel() = factory.create(idHabit)
}