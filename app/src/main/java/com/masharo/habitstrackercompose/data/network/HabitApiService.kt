package com.masharo.habitstrackercompose.data.network

import com.masharo.habitstrackercompose.data.model.HabitNetwork
import com.masharo.habitstrackercompose.data.model.HabitNetworkUID
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitApiService {

    @GET("habit")
    suspend fun getHabits(): List<HabitNetwork>

    @PUT("habit")
    suspend fun addOrUpdateHabit(@Body habit: HabitNetwork): HabitNetworkUID

}