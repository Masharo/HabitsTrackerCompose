package com.masharo.habitstrackercompose.di.dagger

import android.content.Context
import androidx.room.Update
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.ViewModelComponent
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitListViewModel
import com.masharo.habitstrackercompose.worker.CreateHabitWorker
import com.masharo.habitstrackercompose.worker.DownloadHabitsWorker
import com.masharo.habitstrackercompose.worker.UpdateHabitWorker
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(createHabitWorker: CreateHabitWorker)
    fun inject(updateHabitWorker: UpdateHabitWorker)
    fun inject(downloadHabitsWorker: DownloadHabitsWorker)

    fun viewModelComponent(): ViewModelComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

}