package com.masharo.habitstrackercompose.data

import androidx.room.*
import com.masharo.habitstrackercompose.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id_habit = :id LIMIT 1")
    fun getHabitById(id: Long): Habit?

}