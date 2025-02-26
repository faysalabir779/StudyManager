package com.example.studymanager.data.repository

import com.example.studymanager.data.local.TaskDao
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.doamin.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskBySubjectId(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getTaskForSubject(taskSubjectId: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }
}