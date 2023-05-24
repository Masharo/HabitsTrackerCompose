package com.masharo.habitstrackercompose.di.dagger.viewmodelutils

import com.masharo.habitstrackercompose.di.dagger.AppComponent
import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModelFactory
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModelFactory
import javax.inject.Inject

class Test(
    appComponent: AppComponent,
    private val idHabit: Long?
) {

    init {
        appComponent.inject(this)
    }

    @Inject
    lateinit var factory: HabitViewModelFactory.Factory

    fun getHabitViewModelFactory() = factory.create(idHabit)

}

class Test2(
    appComponent: AppComponent
) {

    init {
        appComponent.inject(this)
    }

    @Inject
    lateinit var factory: HabitListViewModelFactory

    fun getHabitListViewModelFactory() = factory
}