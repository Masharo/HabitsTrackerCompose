package com.masharo.habitstrackercompose.di.dagger.viewmodelutils

import android.content.Context
import com.masharo.habitstrackercompose.di.dagger.AppModule
import com.masharo.habitstrackercompose.di.dagger.DataModule
import dagger.BindsInstance
import dagger.Component

//@Subcomponent(modules = [ViewModelModule::class])
//interface ViewModelComponent {
//
//    @Subcomponent.Builder
//    interface Builder {
//
//        fun build(): ViewModelComponent
//
//    }
//
//}

@Component(modules = [AppModule::class, DataModule::class])
@ViewModel
interface ViewModelComponent {

    fun inject(test: Test)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ViewModelComponent

    }
}