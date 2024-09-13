package com.example.moneymanage.features.task.repository

import com.example.moneymanage.data.local.entity.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getAllTask(
        markAs: String
    ): Flow<List<TaskModel>>

    suspend fun insert(taskModel: TaskModel)

    suspend fun delete(taskModel: TaskModel)

    suspend fun update(taskModel: TaskModel)

}