package com.masharo.habitstrackercompose.network

interface NetworkHabitRepository {

    fun downloadHabits()

    fun updateHabit(id: Long)

    fun createHabit(id: Long)

}