package com.example.moneymanage.features.notes.ui

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

    fun insert(noteModel: NoteModel) = viewModelScope.launch {
        repository.insert(noteModel)
    }

    fun delete(noteModel: NoteModel) = viewModelScope.launch {
        repository.delete(noteModel)
    }

    fun update(noteModel: NoteModel) = viewModelScope.launch {
        repository.update(noteModel)
    }
}
