package com.example.studymanager.data.di

import androidx.room.Insert
import com.example.studymanager.data.repository.SessionRepositoryImpl
import com.example.studymanager.data.repository.SubjectRepositoryImpl
import com.example.studymanager.data.repository.TaskRepositoryImpl
import com.example.studymanager.doamin.repository.SessionRepository
import com.example.studymanager.doamin.repository.SubjectRepository
import com.example.studymanager.doamin.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}