package com.masharo.habitstrackercompose.repository

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

interface DBHabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): Flow<List<Habit>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): Flow<List<Habit>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): Flow<List<Habit>>

    suspend fun getHabitById(id: Long): Habit?

    suspend fun update(habit: Habit)

    suspend fun insert(habit: Habit): Long

}