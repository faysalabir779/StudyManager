package com.example.studymanager.doamin.repository

import com.example.studymanager.doamin.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession(session: Session  )

    fun getAllSession(): Flow<List<Session>>

    fun getRecentFiveSession(): Flow<List<Session>>

    fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalSessionDurationBySubject(subjectId: Int): Flow<Long>

}