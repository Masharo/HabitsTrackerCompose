package com.masharo.habitstrackercompose.data.network

import android.content.Context
import androidx.work.WorkManager

class NetworkHabitRepositoryImpl(
    context: Context
) : NetworkHabitRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun downloadHabits() {
        workManager
    }

    override fun updateHabit() {
        TODO("Not yet implemented")
    }

    override fun saveHabit() {
        TODO("Not yet implemented")
    }

}