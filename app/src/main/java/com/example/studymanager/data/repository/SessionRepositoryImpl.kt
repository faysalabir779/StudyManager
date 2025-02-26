package com.example.studymanager.data.repository

import com.example.studymanager.data.local.SessionDao
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(private val sessionDao: SessionDao):SessionRepository {
    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(sessionId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllSession(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentSessionForSubject(subjectId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSessionBySubjectId(subjectId: Int) {
        TODO("Not yet implemented")
    }
}