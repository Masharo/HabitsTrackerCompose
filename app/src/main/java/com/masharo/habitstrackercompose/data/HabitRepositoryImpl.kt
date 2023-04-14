package com.masharo.habitstrackercompose.data

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.model.Habit

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository{

    private fun titleTemplate(title: String) = "%$title%"

    override fun getAllHabitsLikeTitleOrderByPriority(
        title: String,
        isAsc: Boolean
    ): LiveData<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByPriority(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderById(
        title: String,
        isAsc: Boolean
    ): LiveData<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderById(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderByCount(
        title: String,
        isAsc: Boolean
    ): LiveData<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByCount(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override suspend fun getHabitById(id: Long): Habit? = habitDao.getHabitById(id)

    override suspend fun update(habit: Habit) = habitDao.update(habit)

    override suspend fun insert(habit: Habit) = habitDao.insert(habit)

}