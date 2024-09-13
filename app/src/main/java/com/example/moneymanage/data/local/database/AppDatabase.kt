package com.example.moneymanage.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneymanage.data.local.dao.MoneyManageDao
import com.example.moneymanage.data.local.dao.NoteDao
import com.example.moneymanage.data.local.dao.TaskDao
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel

@Database(entities = [NoteModel::class, MoneyManageModel::class, TaskModel::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun moneyManageDao(): MoneyManageDao
    abstract fun taskDao(): TaskDao
}
