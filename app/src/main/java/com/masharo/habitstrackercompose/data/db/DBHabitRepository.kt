package com.masharo.habitstrackercompose.data.db

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.data.model.Habit

interface DBHabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): LiveData<List<Habit>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): LiveData<List<Habit>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): LiveData<List<Habit>>

    suspend fun getHabitById(id: Long): Habit?

    suspend fun update(habit: Habit)

    suspend fun insert(habit: Habit)

}