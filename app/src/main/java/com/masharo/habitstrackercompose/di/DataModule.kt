package com.masharo.habitstrackercompose.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.masharo.habitstrackercompose.data.db.HabitDao
import com.masharo.habitstrackercompose.data.db.HabitDatabase
import com.masharo.habitstrackercompose.data.db.DBHabitRepository
import com.masharo.habitstrackercompose.data.db.DBHabitRepositoryImpl
import com.masharo.habitstrackercompose.data.network.HABIT_API_BASE_URL
import com.masharo.habitstrackercompose.data.network.HABIT_API_TYPE_DATA
import com.masharo.habitstrackercompose.data.network.HabitApiService
import com.masharo.habitstrackercompose.data.network.HabitHeaderInterceptor
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single<HabitDao> {
        HabitDatabase.getDatabase(
            context = get()
        ).habitDao()
    }

    single<DBHabitRepository> {
        DBHabitRepositoryImpl(
            habitDao = get()
        )
    }

    factory<HabitApiService> {
        Retrofit
            .Builder()
                .addConverterFactory(Json.asConverterFactory(HABIT_API_TYPE_DATA.toMediaType()))
                .baseUrl(HABIT_API_BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HabitHeaderInterceptor())
                        .build()
                )
                .build()
            .create(HabitApiService::class.java)
    }

    factory<Interceptor> {
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                TODO("Not yet implemented")
            }

        }
    }

}