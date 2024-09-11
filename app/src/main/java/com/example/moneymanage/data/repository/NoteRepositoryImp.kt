package com.example.moneymanage.data.repository

import com.example.moneymanage.data.local.dao.NoteDao
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.features.notes.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteModel>> = noteDao.getAllNotes()

    override suspend fun insert(noteModel: NoteModel) = noteDao.insert(noteModel)

    override suspend fun delete(noteModel: NoteModel) = noteDao.delete(noteModel)

    override suspend fun update(noteModel: NoteModel) = noteDao.update(noteModel)
}
