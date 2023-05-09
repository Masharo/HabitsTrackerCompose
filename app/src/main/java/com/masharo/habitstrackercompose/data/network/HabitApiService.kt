package com.masharo.habitstrackercompose.data.network

import com.masharo.habitstrackercompose.data.model.HabitNetwork
import retrofit2.http.GET

interface HabitApiService {

    @GET("habit")
    suspend fun getHabits(): List<HabitNetwork>

//    @PUT("habit")
//    suspend fun add

}