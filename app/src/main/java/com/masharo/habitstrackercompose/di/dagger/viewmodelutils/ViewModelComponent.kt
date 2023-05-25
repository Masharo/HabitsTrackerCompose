package com.masharo.habitstrackercompose.di.dagger.viewmodelutils

import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModel
import dagger.Subcomponent

@ViewModel
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    fun inject(test: HabitViewModelSupportInject)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ViewModelComponent

    }

    fun getHabitListViewModel(): HabitListViewModel

}