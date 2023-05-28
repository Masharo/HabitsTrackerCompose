package com.masharo.habitstrackercompose.app

import android.app.Application
import com.masharo.habitstrackercompose.di.appModule
import com.masharo.habitstrackercompose.di.dataModule
import com.masharo.habitstrackercompose.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }

    }

}