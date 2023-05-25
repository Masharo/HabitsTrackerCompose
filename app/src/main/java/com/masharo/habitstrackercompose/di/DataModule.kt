package com.masharo.habitstrackercompose.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.masharo.habitstrackercompose.db.DBHabitRepository
import com.masharo.habitstrackercompose.db.DBHabitRepositoryImpl
import com.masharo.habitstrackercompose.db.HABIT_DB_NAME
import com.masharo.habitstrackercompose.db.HabitDao
import com.masharo.habitstrackercompose.db.HabitDatabase
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

    single<HabitDao> {
        Room
            .databaseBuilder(
                context = get(),
                klass = HabitDatabase::class.java,
                name = HABIT_DB_NAME
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