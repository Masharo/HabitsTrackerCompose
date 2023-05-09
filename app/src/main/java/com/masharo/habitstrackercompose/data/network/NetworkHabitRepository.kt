package com.masharo.habitstrackercompose.data.network

interface NetworkHabitRepository {

    fun downloadHabits()

    fun updateHabit(id: Long)

    fun createHabit(id: Long)

}