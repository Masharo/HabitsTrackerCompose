package com.masharo.habitstrackercompose.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masharo.habitstrackercompose.data.model.HabitDB

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHabits(vararg habits: HabitDB)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHabit(habit: HabitDB): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(habit: HabitDB)

    @Query("""DELETE FROM habits""")
    suspend fun deleteAll()

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN priority END ASC,
            CASE WHEN :isAsc = 0 THEN priority END DESC
    """)
    fun getAllHabitsLikeTitleOrderByPriority(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN id_habit END ASC,
            CASE WHEN :isAsc = 0 THEN id_habit END DESC
    """)
    fun getAllHabitsLikeTitleOrderById(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    @Query("""  
        SELECT * 
        FROM habits 
        WHERE title LIKE :title
        ORDER BY 
            CASE WHEN :isAsc = 1 THEN count END ASC,
            CASE WHEN :isAsc = 0 THEN count END DESC
    """)
    fun getAllHabitsLikeTitleOrderByCount(title: String, isAsc: Boolean): LiveData<List<HabitDB>>

    @Query("SELECT * FROM habits WHERE id_habit = :id LIMIT 1")
    suspend fun getHabitById(id: Long): HabitDB?

}