package com.example.studymanager.doamin.repository

import com.example.studymanager.doamin.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    suspend fun upsertSubject(subject: Subject)

    fun getTotalSubjectCount(): Flow<Int>

    fun getTotalGoalHours(): Flow<Float>

    suspend fun getSubjectById(subjectId: Int): Subject?

    suspend fun deleteSubject(subjectId: Int)

    fun getAllSubject(): Flow<List<Subject>>
}