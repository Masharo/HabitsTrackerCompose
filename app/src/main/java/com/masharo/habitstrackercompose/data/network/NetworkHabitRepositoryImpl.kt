package com.masharo.habitstrackercompose.data.network

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.masharo.habitstrackercompose.data.worker.GetHabitsWorker

class NetworkHabitRepositoryImpl(
    context: Context
) : NetworkHabitRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun downloadHabits() {
        workManager
            .beginUniqueWork(
                WORK_UPDATE_HABITS_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Companion.from(GetHabitsWorker::class.java)
            )
            .enqueue()
    }

    override fun updateHabit() {
        TODO("Not yet implemented")
    }

    override fun saveHabit() {
        TODO("Not yet implemented")
    }

}