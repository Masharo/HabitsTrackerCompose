package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository{

    override fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()

    override fun getHabitById(id: Long): Habit? = habitDao.getHabitById(id)

    override suspend fun update(habit: Habit) = habitDao.update(habit)

    override suspend fun insert(habit: Habit) = habitDao.insert(habit)

}