package com.masharo.habitstrackercompose.data.db

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.data.model.HabitDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBHabitRepositoryImpl(
    private val habitDao: HabitDao
) : DBHabitRepository {

    private fun titleTemplate(title: String) = "%$title%"

    override fun getAllHabitsLikeTitleOrderByPriority(
        title: String,
        isAsc: Boolean
    ): LiveData<List<HabitDB>> = habitDao.getAllHabitsLikeTitleOrderByPriority(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderById(
        title: String,
        isAsc: Boolean
    ): LiveData<List<HabitDB>> = habitDao.getAllHabitsLikeTitleOrderById(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override fun getAllHabitsLikeTitleOrderByCount(
        title: String,
        isAsc: Boolean
    ): LiveData<List<HabitDB>> = habitDao.getAllHabitsLikeTitleOrderByCount(
        title = titleTemplate(title),
        isAsc = isAsc
    )

    override suspend fun getHabitById(id: Long): HabitDB? {
       return withContext(Dispatchers.IO) {
           habitDao.getHabitById(id)
       }
    }

    override suspend fun update(habit: HabitDB) {
        withContext(Dispatchers.IO) {
            habitDao.update(habit)
        }
    }

    override suspend fun insert(habit: HabitDB): Long {
        return withContext(Dispatchers.IO) {
            habitDao.insertHabit(habit)
        }
    }

}