package com.masharo.habitstrackercompose.network

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.masharo.habitstrackercompose.worker.CreateHabitWorker
import com.masharo.habitstrackercompose.worker.DownloadHabitsWorker
import com.masharo.habitstrackercompose.worker.UpdateHabitWorker

class NetworkHabitRepositoryImpl(
    context: Context
) : NetworkHabitRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun downloadHabits() {
        workManager
            .beginUniqueWork(
                WORK_DOWNLOAD_HABITS_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<DownloadHabitsWorker>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .build()
            )
            .enqueue()
    }

    override fun updateHabit(id: Long) {
        createOrUpdateHabit<UpdateHabitWorker>(id)
    }

    override fun createHabit(id: Long) {
        createOrUpdateHabit<CreateHabitWorker>(id)
    }

    private inline fun <reified W : ListenableWorker> createOrUpdateHabit(id: Long) {
        workManager
            .beginWith(
                OneTimeWorkRequestBuilder<W>()
                    .setInputData(
                        Data.Builder()
                            .putLong(HABIT_ID, id)
                            .build()
                    )
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .build()
            )
            .enqueue()
    }

}