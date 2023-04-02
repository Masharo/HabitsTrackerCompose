package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Long): Flow<Habit?>

    suspend fun update(habit: Habit)

    suspend fun insert(habit: Habit)

}