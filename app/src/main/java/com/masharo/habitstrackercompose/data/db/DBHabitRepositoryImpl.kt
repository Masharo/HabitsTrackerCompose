package com.masharo.habitstrackercompose.data.db

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.data.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBHabitRepositoryImpl(
    private val habitDao: HabitDao
) : DBHabitRepository {

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

    override suspend fun getHabitById(id: Long): Habit? {
       return withContext(Dispatchers.IO) {
           habitDao.getHabitById(id)
       }
    }

    override suspend fun update(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitDao.update(habit)
        }
    }

    override suspend fun insert(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitDao.insert(habit)
        }
    }

}