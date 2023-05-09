package com.masharo.habitstrackercompose.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habitstrackercompose.data.db.HabitDao
import com.masharo.habitstrackercompose.data.model.toHabitNetwork
import com.masharo.habitstrackercompose.data.network.HABIT_ID
import com.masharo.habitstrackercompose.data.network.HabitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateHabitWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val api: HabitApiService by inject()
    private val db: HabitDao by inject()

    override suspend fun doWork(): Result {
        val id = inputData.getLong(HABIT_ID, 0)
        return withContext(Dispatchers.IO) {
            val habit = db.getHabitById(id) ?: return@withContext Result.failure()
            val habitNetworkUID = api.addOrUpdateHabit(habit.toHabitNetwork())
            db.update(
                habit.copy(
                    uid = habitNetworkUID.uid
                )
            )
            Result.success()
        }
    }

}