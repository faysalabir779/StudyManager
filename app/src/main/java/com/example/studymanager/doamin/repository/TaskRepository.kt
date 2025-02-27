package com.example.studymanager.doamin.repository

import com.example.studymanager.doamin.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getUpcomingTasksForSubject(subjectId: Int): Flow<List<Task>>

    fun getCompletedTaskForSubject(subjectId: Int): Flow<List<Task>>

    fun getAllUpcomingTask(): Flow<List<Task>>


}