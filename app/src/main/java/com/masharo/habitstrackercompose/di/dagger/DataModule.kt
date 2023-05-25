package com.masharo.habitstrackercompose.di.dagger

import android.content.Context
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
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [DBModule::class, NetworkModule::class])
class DataModule

@Module
class DBModule {

    @Provides
    fun provideHabitDao(context: Context): com.masharo.habitstrackercompose.db.HabitDao {
        return Room
            .databaseBuilder(
                context = context,
                klass = com.masharo.habitstrackercompose.db.HabitDatabase::class.java,
                name = com.masharo.habitstrackercompose.db.HABIT_DB_NAME
            )
            .build()
            .habitDao()
    }

    @Provides
    fun provideDBHabitRepository(habitDao: com.masharo.habitstrackercompose.db.HabitDao): com.masharo.habitstrackercompose.db.DBHabitRepository {
        return com.masharo.habitstrackercompose.db.DBHabitRepositoryImpl(habitDao = habitDao)
    }

}

@Module
class NetworkModule {

    @Provides
    fun provideNetworkHabitRepository(context: Context): com.masharo.habitstrackercompose.network.NetworkHabitRepository {
        return com.masharo.habitstrackercompose.network.NetworkHabitRepositoryImpl(context = context)
    }

    @Provides
    fun provideHabitApiService(): com.masharo.habitstrackercompose.network.HabitApiService {
        return Retrofit
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