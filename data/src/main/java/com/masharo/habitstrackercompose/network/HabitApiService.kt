package com.masharo.habitstrackercompose.network

import com.masharo.habitstrackercompose.model.HabitCountDoneNetwork
import com.masharo.habitstrackercompose.model.HabitNetwork
import com.masharo.habitstrackercompose.model.HabitNetworkUID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitApiService {

    @GET("habit")
    suspend fun getHabits(): Response<List<HabitNetwork>>

    @PUT("habit")
    suspend fun createOrUpdateHabit(@Body habit: HabitNetwork): Response<HabitNetworkUID>

    @POST("habit_done")
    suspend fun incCountDoneHabit(@Body habitCountDoneNetwork: HabitCountDoneNetwork): Response<Void>

}