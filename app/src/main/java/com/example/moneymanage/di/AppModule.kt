package com.example.moneymanage.di

import android.content.Context
import androidx.room.Room
import com.example.moneymanage.data.local.dao.MoneyManageDao
import com.example.moneymanage.data.local.dao.NoteDao
import com.example.moneymanage.data.local.database.AppDatabase
import com.example.moneymanage.data.repository.MoneyManageRepositoryImp
import com.example.moneymanage.data.repository.NoteRepositoryImp
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
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepositoryImp {
        return NoteRepositoryImp(noteDao)
    }

    @Provides
    @Singleton
    fun provideMoneyManageRepository(moneyManageDao: MoneyManageDao): MoneyManageRepositoryImp {
        return MoneyManageRepositoryImp(moneyManageDao)
    }

}
