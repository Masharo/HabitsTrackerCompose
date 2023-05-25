package com.masharo.habitstrackercompose.di.dagger

import android.content.Context
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.ViewModelComponent
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {

    fun viewModelComponent(): ViewModelComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

}