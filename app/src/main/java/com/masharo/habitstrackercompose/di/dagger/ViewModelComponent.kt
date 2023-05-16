package com.masharo.habitstrackercompose.di.dagger

import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ViewModelComponent

    }

}