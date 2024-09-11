package com.example.moneymanage.features.notes.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.features.notes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val allNotes = repository.getAllNotes()

    val notes: LiveData<List<NoteModel>> = allNotes.asLiveData()

    fun insert(
        context: Context,
        noteModel: NoteModel,
        onInsert: () -> Unit
    ) = viewModelScope.launch {
        if (noteModel.description == ""){
            Toast.makeText(context, "Enter Description", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (noteModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.insert(noteModel)
        onInsert()
    }

    fun delete(noteModel: NoteModel) = viewModelScope.launch {
        repository.delete(noteModel)
    }

    fun update(
        context: Context,
        noteModel: NoteModel,
        onUpdate: () -> Unit
    ) = viewModelScope.launch {
        if (noteModel.description == ""){
            Toast.makeText(context, "Enter Description", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (noteModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.update(noteModel)
        onUpdate()
    }
}
