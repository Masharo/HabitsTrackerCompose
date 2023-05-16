package com.masharo.habitstrackercompose.app

import android.app.Application
import android.content.Context
import com.masharo.habitstrackercompose.di.dagger.AppComponent
import com.masharo.habitstrackercompose.di.dagger.DaggerAppComponent
import com.masharo.habitstrackercompose.di.dagger.ViewModelComponent

class App : Application() {

    lateinit var appComponent: AppComponent
    lateinit var viewModelComponent: ViewModelComponent

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

val Context.toApplicationContext: Context
    get() = when(this) {
        is App -> this
        else -> applicationContext
    }

val Context.appComponent: AppComponent
    get() = toApplicationContext.appComponent

val Context.viewModelComponent: AppComponent
    get() = toApplicationContext.viewModelComponent