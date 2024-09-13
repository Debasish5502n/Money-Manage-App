package com.example.moneymanage.di

import android.content.Context
import androidx.room.Room
import com.example.moneymanage.data.local.dao.MoneyManageDao
import com.example.moneymanage.data.local.dao.NoteDao
import com.example.moneymanage.data.local.dao.TaskDao
import com.example.moneymanage.data.local.database.AppDatabase
import com.example.moneymanage.data.repository.MoneyManageRepositoryImp
import com.example.moneymanage.data.repository.NoteRepositoryImp
import com.example.moneymanage.data.repository.TaskRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    fun provideMoneyManageDao(appDatabase: AppDatabase): MoneyManageDao {
        return appDatabase.moneyManageDao()
    }

    @Provides
    fun provideNoteManageDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepositoryImp {
        return NoteRepositoryImp(noteDao)
    }

    @Provides
    @Singleton
    fun provideMoneyManageRepository(moneyManageDao: MoneyManageDao): MoneyManageRepositoryImp {
        return MoneyManageRepositoryImp(moneyManageDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepositoryImp {
        return TaskRepositoryImp(taskDao)
    }

}
