package com.masharo.habitstrackercompose.repository

interface NetworkHabitRepository {

    fun downloadHabits()

    fun updateHabit(id: Long)

    fun createHabit(id: Long)

    suspend fun incCountReadyHabit(uid: String)

}