package com.masharo.habitstrackercompose.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.masharo.habitstrackercompose.db.HabitDao
import com.masharo.habitstrackercompose.db.HabitDatabase
import com.masharo.habitstrackercompose.db.DBHabitRepository
import com.masharo.habitstrackercompose.db.DBHabitRepositoryImpl
import com.masharo.habitstrackercompose.db.HABIT_DB_NAME
import com.masharo.habitstrackercompose.network.HABIT_API_BASE_URL
import com.masharo.habitstrackercompose.network.HABIT_API_TYPE_DATA
import com.masharo.habitstrackercompose.network.HabitApiService
import com.masharo.habitstrackercompose.network.HabitHeaderInterceptor
import com.masharo.habitstrackercompose.network.NetworkHabitRepository
import com.masharo.habitstrackercompose.network.NetworkHabitRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single<com.masharo.habitstrackercompose.db.HabitDao> {
        Room
            .databaseBuilder(
                context = get(),
                klass = com.masharo.habitstrackercompose.db.HabitDatabase::class.java,
                name = com.masharo.habitstrackercompose.db.HABIT_DB_NAME
            )
            .build()
            .habitDao()
    }

    single<com.masharo.habitstrackercompose.db.DBHabitRepository> {
        com.masharo.habitstrackercompose.db.DBHabitRepositoryImpl(
            habitDao = get()
        )
    }

    single<com.masharo.habitstrackercompose.network.NetworkHabitRepository> {
        com.masharo.habitstrackercompose.network.NetworkHabitRepositoryImpl(
            context = get()
        )
    }

    single<com.masharo.habitstrackercompose.network.HabitApiService> {
        Retrofit
            .Builder()
                .addConverterFactory(Json.asConverterFactory(com.masharo.habitstrackercompose.network.HABIT_API_TYPE_DATA.toMediaType()))
                .baseUrl(com.masharo.habitstrackercompose.network.HABIT_API_BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(com.masharo.habitstrackercompose.network.HabitHeaderInterceptor())
                        .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                HttpLoggingInterceptor.Level.BODY
                            }
                        )
                        .build()
                )
                .build()
            .create(com.masharo.habitstrackercompose.network.HabitApiService::class.java)
    }

}