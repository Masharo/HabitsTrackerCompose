package com.masharo.habitstrackercompose.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masharo.habitstrackercompose.model.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(habit: Habit)

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN priority END ASC,
            CASE WHEN :isAsc = 0 THEN priority END DESC
    """)
    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): LiveData<List<Habit>>

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN id_habit END ASC,
            CASE WHEN :isAsc = 0 THEN id_habit END DESC
    """)
    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): LiveData<List<Habit>>

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN count END ASC,
            CASE WHEN :isAsc = 0 THEN count END DESC
    """)
    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id_habit = :id LIMIT 1")
    suspend fun getHabitById(id: Long): Habit?

}