package com.masharo.habitstrackercompose.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habitstrackercompose.db.HabitDao
import com.masharo.habitstrackercompose.model.toHabitNetwork
import com.masharo.habitstrackercompose.network.HABIT_ID
import com.masharo.habitstrackercompose.network.HabitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class CreateHabitWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    @Inject lateinit var api: HabitApiService
    @Inject lateinit var db: HabitDao

    init {

    }

    override suspend fun doWork(): Result {
        val id = inputData.getLong(HABIT_ID, 0)
        return withContext(Dispatchers.IO) {
            try {
                val habit = db.getHabitById(id) ?: return@withContext Result.failure()
                val response = api.createOrUpdateHabit(habit.toHabitNetwork())
                response.errorBody()?.let {
                    return@withContext Result.retry()
                }
                response.body()?.let { habitNetworkUID ->
                    db.update(
                        habit.copy(
                            uid = habitNetworkUID.uid
                        )
                    )
                    return@withContext Result.success()
                }
                Result.failure()
            } catch (ex: IOException) {
                Result.retry()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }

}