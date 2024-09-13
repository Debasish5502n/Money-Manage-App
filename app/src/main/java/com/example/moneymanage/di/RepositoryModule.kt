package com.example.moneymanage.di

import com.example.moneymanage.data.repository.MoneyManageRepositoryImp
import com.example.moneymanage.data.repository.NoteRepositoryImp
import com.example.moneymanage.data.repository.TaskRepositoryImp
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.features.task.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesMovieRepository(
        movieRepositoryImp: NoteRepositoryImp
    ): NoteRepository

    @Binds
    abstract fun providesMoneyManageRepository(
        moneyManageRepositoryImp: MoneyManageRepositoryImp
    ): MoneyManageRepository

    @Binds
    abstract fun providesTaskManageRepository(
        taskRepositoryImp: TaskRepositoryImp
    ): TaskRepository

}