package com.masharo.habitstrackercompose.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.masharo.habitstrackercompose.data.db.HabitDao
import com.masharo.habitstrackercompose.data.db.HabitDatabase
import com.masharo.habitstrackercompose.data.db.DBHabitRepository
import com.masharo.habitstrackercompose.data.db.DBHabitRepositoryImpl
import com.masharo.habitstrackercompose.data.network.HABIT_API_BASE_URL
import com.masharo.habitstrackercompose.data.network.HABIT_API_TYPE_DATA
import com.masharo.habitstrackercompose.data.network.HabitApiService
import com.masharo.habitstrackercompose.data.network.HabitHeaderInterceptor
import com.masharo.habitstrackercompose.data.network.NetworkHabitRepository
import com.masharo.habitstrackercompose.data.network.NetworkHabitRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single<HabitDao> {
        Room
            .databaseBuilder(
                context = get(),
                klass = HabitDatabase::class.java,
                name = "habit_database"
            )
            .build()
            .habitDao()
    }

    single<DBHabitRepository> {
        DBHabitRepositoryImpl(
            habitDao = get()
        )
    }

    single<NetworkHabitRepository> {
        NetworkHabitRepositoryImpl(
            context = get()
        )
    }

    single<HabitApiService> {
        Retrofit
            .Builder()
                .addConverterFactory(Json.asConverterFactory(HABIT_API_TYPE_DATA.toMediaType()))
                .baseUrl(HABIT_API_BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HabitHeaderInterceptor())
                        .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                HttpLoggingInterceptor.Level.BODY
                            }
                        )
                        .build()
                )
                .build()
            .create(HabitApiService::class.java)
    }

}