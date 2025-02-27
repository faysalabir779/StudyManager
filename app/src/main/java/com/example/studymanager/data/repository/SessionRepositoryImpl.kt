package com.example.studymanager.data.repository

import com.example.studymanager.data.local.SessionDao
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
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

    override fun getRecentFiveSession(): Flow<List<Session>> {
        return sessionDao.getAllSession().take(count = 5)
    }

    override fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

}