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

@Module(includes = [DBModule::class, NetworkModule::class])
class DataModule

@Module
class DBModule {

    @Provides
    fun provideHabitDao(context: Context): HabitDao {
        return Room
            .databaseBuilder(
                context = context,
                klass = HabitDatabase::class.java,
                name = HABIT_DB_NAME
            )
            .build()
            .habitDao()
    }

    @Provides
    fun provideDBHabitRepository(habitDao: HabitDao): DBHabitRepository {
        return DBHabitRepositoryImpl(habitDao = habitDao)
    }

}

@Module
class NetworkModule {

    @Provides
    fun provideNetworkHabitRepository(context: Context): NetworkHabitRepository {
        return NetworkHabitRepositoryImpl(context = context)
    }

    @Provides
    fun provideHabitApiService(): HabitApiService {
        return Retrofit
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