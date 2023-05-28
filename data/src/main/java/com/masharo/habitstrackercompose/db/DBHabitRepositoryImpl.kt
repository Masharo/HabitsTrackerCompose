package com.masharo.habitstrackercompose.db

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.toHabit
import com.masharo.habitstrackercompose.model.toHabitDB
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DBHabitRepositoryImpl(
    private val habitDao: HabitDao
) : DBHabitRepository {

    private fun titleTemplate(title: String) = "%$title%"

    override fun getAllHabitsLikeTitleOrderByPriority(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByPriority(
        title = titleTemplate(title),
        isAsc = isAsc
    ).map {
        list -> list.map { habit ->
            habit.toHabit()
        }
    }

    override fun getAllHabitsLikeTitleOrderById(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderById(
        title = titleTemplate(title),
        isAsc = isAsc
    ).map {
        list -> list.map { habit ->
            habit.toHabit()
        }
    }

    override fun getAllHabitsLikeTitleOrderByCount(
        title: String,
        isAsc: Boolean
    ): Flow<List<Habit>> = habitDao.getAllHabitsLikeTitleOrderByCount(
        title = titleTemplate(title),
        isAsc = isAsc
    ).map {
        list -> list.map { habit ->
            habit.toHabit()
        }
    }

    override suspend fun getHabitById(id: Long): Habit? {
       return withContext(Dispatchers.IO) {
           habitDao.getHabitById(id)?.toHabit()
       }
    }

    override suspend fun update(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitDao.update(habit.toHabitDB())
        }
    }

    override suspend fun insert(habit: Habit): Long {
        return withContext(Dispatchers.IO) {
            habitDao.insertHabit(habit.toHabitDB())
        }
    }

}