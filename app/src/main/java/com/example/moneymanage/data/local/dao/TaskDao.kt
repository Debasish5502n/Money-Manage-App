package com.example.moneymanage.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY date DESC")
    fun getAllTasks(): Flow<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskModel: TaskModel)

    @Delete
    suspend fun delete(taskModel: TaskModel)

    @Update
    suspend fun update(taskModel: TaskModel)
}
