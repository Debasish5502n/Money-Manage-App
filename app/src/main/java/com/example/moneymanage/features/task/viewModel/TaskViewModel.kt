package com.example.moneymanage.features.task.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.features.task.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    // MutableStateFlow to hold and modify the list
    private val _allTasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val allTasks: StateFlow<List<TaskModel>> get() = _allTasks

    fun getTasks(markAs: String){
        viewModelScope.launch {
            repository.getAllTask(markAs).collect { todayList ->
                _allTasks.value = todayList
            }
        }
    }

    fun insert(
        context: Context,
        taskModel: TaskModel,
        onInsert: () -> Unit
    ) = viewModelScope.launch {
        if (taskModel.description == ""){
            Toast.makeText(context, "Enter Description", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (taskModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.insert(taskModel)
        onInsert()
    }

    fun delete(taskModel: TaskModel) = viewModelScope.launch {
        repository.delete(taskModel)
    }

    fun update(
        context: Context,
        taskModel: TaskModel,
        onUpdate: () -> Unit
    ) = viewModelScope.launch {
        if (taskModel.description == ""){
            Toast.makeText(context, "Enter Description", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (taskModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.update(taskModel)
        onUpdate()
    }
}