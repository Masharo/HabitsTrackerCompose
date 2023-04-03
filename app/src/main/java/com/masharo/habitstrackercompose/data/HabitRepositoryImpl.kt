package com.masharo.habitstrackercompose.data

import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository{

    private fun titleTemplate(title: String) = "%$title%"

    override fun getAllHabitsLikeTitleOrderByPriority(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByPriority(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderById(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderById(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderByCount(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByCount(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getHabitById(id: Long): Habit? = habitDao.getHabitById(id)

    override suspend fun update(habit: Habit) = habitDao.update(habit)

    override suspend fun insert(habit: Habit) = habitDao.insert(habit)

}