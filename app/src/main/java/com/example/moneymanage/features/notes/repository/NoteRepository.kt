package com.example.moneymanage.features.notes.repository

import com.example.moneymanage.data.local.entity.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<NoteModel>>

    suspend fun insert(noteModel: NoteModel)

    suspend fun delete(noteModel: NoteModel)

    suspend fun update(noteModel: NoteModel)

}