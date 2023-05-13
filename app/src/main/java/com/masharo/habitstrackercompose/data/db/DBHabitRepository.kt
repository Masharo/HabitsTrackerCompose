package com.masharo.habitstrackercompose.data.db

import com.masharo.habitstrackercompose.data.model.HabitDB
import kotlinx.coroutines.flow.Flow

interface DBHabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): Flow<List<HabitDB>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): Flow<List<HabitDB>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): Flow<List<HabitDB>>

    suspend fun getHabitById(id: Long): HabitDB?

    suspend fun update(habit: HabitDB)


    suspend fun insert(habit: HabitDB): Long

}