package com.masharo.habitstrackercompose.data.network

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.masharo.habitstrackercompose.data.worker.CreateHabitWorker
import com.masharo.habitstrackercompose.data.worker.GetHabitsWorker
import com.masharo.habitstrackercompose.data.worker.UpdateHabitWorker

class NetworkHabitRepositoryImpl(
    context: Context
) : NetworkHabitRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun downloadHabits() {
        workManager
            .beginUniqueWork(
                WORK_DOWNLOAD_HABITS_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(GetHabitsWorker::class.java)
            )
            .enqueue()
    }

    override fun updateHabit(id: Long) {
        workManager
            .beginWith(
                OneTimeWorkRequestBuilder<UpdateHabitWorker>()
                    .setInputData(
                        Data.Builder()
                            .putLong(HABIT_ID, id)
                            .build()
                    )
                    .build()
            )
            .enqueue()
    }

    override fun createHabit(id: Long) {
        workManager
            .beginWith(
                OneTimeWorkRequestBuilder<CreateHabitWorker>()
                    .setInputData(
                        Data.Builder()
                            .putLong(HABIT_ID, id)
                            .build()
                    )
                    .build()
            )
            .enqueue()
    }

}