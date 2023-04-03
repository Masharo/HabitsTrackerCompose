package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): Flow<List<Habit>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): Flow<List<Habit>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): Flow<List<Habit>>

    fun getHabitById(id: Long): Habit?

    suspend fun update(habit: Habit)

    suspend fun insert(habit: Habit)

}