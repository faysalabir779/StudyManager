package com.example.studymanager.data.repository

import com.example.studymanager.data.local.SubjectDao
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(private val subjectDao: SubjectDao): SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getTotalGoalHours(): Flow<Float> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        TODO("Not yet implemented")
    }


}