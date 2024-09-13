package com.example.moneymanage.data.repository

import com.example.moneymanage.data.local.dao.TaskDao
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.task.repository.TaskRepository
import com.example.moneymanage.utils.DateModifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getAllTask(markAs: String): Flow<List<TaskModel>> {
        return taskDao.getAllTasks().map { tasksList ->
            tasksList.filter {
                it.markAs.equals(markAs)
            }
        }
    }

    override suspend fun insert(taskModel: TaskModel) {
        return taskDao.insert(taskModel)
    }

    override suspend fun delete(taskModel: TaskModel) {
        return taskDao.delete(taskModel)
    }

    override suspend fun update(taskModel: TaskModel) {
        return taskDao.update(taskModel)
    }

}
