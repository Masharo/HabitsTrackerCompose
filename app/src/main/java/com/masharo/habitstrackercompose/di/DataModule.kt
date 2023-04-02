package com.masharo.habitstrackercompose.di

import com.masharo.habitstrackercompose.data.HabitDao
import com.masharo.habitstrackercompose.data.HabitDatabase
import com.masharo.habitstrackercompose.data.HabitRepository
import com.masharo.habitstrackercompose.data.HabitRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<HabitDao> {
        HabitDatabase.getDatabase(
            context = get()
        ).habitDao()
    }

    single<HabitRepository> {
        HabitRepositoryImpl(
            habitDao = get()
        )
    }

}