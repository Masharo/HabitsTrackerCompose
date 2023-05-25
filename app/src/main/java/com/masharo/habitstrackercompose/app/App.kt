package com.masharo.habitstrackercompose.app

import android.app.Application
import android.content.Context
import com.masharo.habitstrackercompose.di.dagger.AppComponent
import com.masharo.habitstrackercompose.di.dagger.DaggerAppComponent
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.ViewModelComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    lateinit var viewModelComponent: ViewModelComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .context(applicationContext)
            .build()

        viewModelComponent = appComponent
            .viewModelComponent()
            .build()

    }

}

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

val Context.viewModelComponent: ViewModelComponent
    get() = when(this) {
        is App -> viewModelComponent
        else -> this.applicationContext.viewModelComponent
    }