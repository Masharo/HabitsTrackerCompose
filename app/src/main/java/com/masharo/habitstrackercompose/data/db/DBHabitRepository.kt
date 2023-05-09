package com.masharo.habitstrackercompose.data.db

import androidx.lifecycle.LiveData
import com.masharo.habitstrackercompose.data.model.HabitDB

interface DBHabitRepository {

    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    suspend fun getHabitById(id: Long): HabitDB?

    suspend fun update(habit: HabitDB)

    suspend fun insert(habit: HabitDB)

}