package com.masharo.habitstrackercompose.app

import android.app.Application
import com.masharo.habitstrackercompose.di.appModule
import com.masharo.habitstrackercompose.di.dagger.AppComponent
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.ViewModelComponent
import com.masharo.habitstrackercompose.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

//    lateinit var appComponent: AppComponent
//        private set
//
//    lateinit var viewModelComponent: ViewModelComponent
//        private set

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            workManagerFactory()
            androidContext(this@App)
            modules(listOf(appModule, dataModule))
        }

//        appComponent = DaggerAppComponent
//            .builder()
//            .context(applicationContext)
//            .build()
//
//        viewModelComponent = appComponent
//            .viewModelComponent()
//            .build()

    }

}

//val Context.appComponent: AppComponent
//    get() = when(this) {
//        is App -> appComponent
//        else -> this.applicationContext.appComponent
//    }
//
//val Context.viewModelComponent: ViewModelComponent
//    get() = when(this) {
//        is App -> viewModelComponent
//        else -> this.applicationContext.viewModelComponent
//    }