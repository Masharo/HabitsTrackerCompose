package com.masharo.habitstrackercompose.data

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.model.Habit

interface HabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): LiveData<List<Habit>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): LiveData<List<Habit>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): LiveData<List<Habit>>

    fun getHabitById(id: Long): Habit?

    suspend fun update(habit: Habit)

    suspend fun insert(habit: Habit)

}