package com.masharo.habitstrackercompose.di.dagger

import android.content.Context
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.Test
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.Test2
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.ViewModel
import com.masharo.habitstrackercompose.navigate.HabitNavigateState
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModel
import dagger.BindsInstance
import dagger.Component

@ViewModel
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {

//    fun viewModelComponent(): ViewModelComponent.Builder

    fun inject(test: Test)

    fun inject(test: Test2)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

    fun getHabitListViewModel(): HabitListViewModel

}