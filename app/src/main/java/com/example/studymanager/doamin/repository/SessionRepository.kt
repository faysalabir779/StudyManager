package com.example.studymanager.doamin.repository

import com.example.studymanager.doamin.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession(sessionId: Int)

    fun getAllSession(): Flow<List<Session>>

    fun getRecentSessionForSubject(subjectId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long>

    suspend fun deleteSessionBySubjectId(subjectId: Int)



}