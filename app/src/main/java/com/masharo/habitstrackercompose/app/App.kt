package com.masharo.habitstrackercompose.app

import android.app.Application
import androidx.work.WorkerFactory
import com.masharo.habitstrackercompose.di.appModule
import com.masharo.habitstrackercompose.di.dataModule
import com.masharo.habitstrackercompose.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }

    }

}