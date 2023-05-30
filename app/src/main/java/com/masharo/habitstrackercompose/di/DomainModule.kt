package com.masharo.habitstrackercompose.di

import com.masharo.habitstrackercompose.usecase.AddHabitUseCase
import com.masharo.habitstrackercompose.usecase.GetHabitFromCacheUseCase
import com.masharo.habitstrackercompose.usecase.GetHabitsListFromCacheUseCase
import com.masharo.habitstrackercompose.usecase.IncReadyCountHabitUseCase
import com.masharo.habitstrackercompose.usecase.UpdateHabitUseCase
import com.masharo.habitstrackercompose.usecase.UpdateLocalCacheHabitsUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {

    factory<GetHabitsListFromCacheUseCase> {
        GetHabitsListFromCacheUseCase(
            dbHabitRepository = get()
        )
    }

    factory<UpdateLocalCacheHabitsUseCase> {
        UpdateLocalCacheHabitsUseCase(
            networkRepository = get()
        )
    }

    factory<UpdateHabitUseCase> {
        UpdateHabitUseCase(
            dbRepository = get(),
            networkRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<AddHabitUseCase> {
        AddHabitUseCase(
            dbRepository = get(),
            networkRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<GetHabitFromCacheUseCase> {
        GetHabitFromCacheUseCase(
            dbRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<IncReadyCountHabitUseCase> {
        IncReadyCountHabitUseCase(
            dbRepository = get(),
            networkRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }
}