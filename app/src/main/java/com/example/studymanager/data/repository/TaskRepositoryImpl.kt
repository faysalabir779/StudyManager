package com.example.studymanager.data.repository

import com.example.studymanager.data.local.TaskDao
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.doamin.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTasksForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskForSubject(subjectId)
            .map { tasks->
                tasks.filter { it.isCompleted.not() }
            }
            .map { tasks-> sortTask(tasks) }
    }

    override fun getCompletedTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskForSubject(subjectId)
            .map { tasks->
                tasks.filter { it.isCompleted}
            }
            .map { tasks-> sortTask(tasks) }
    }

    override fun getAllUpcomingTask(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
            .map { tasks -> sortTask(tasks) }
    }

    private fun sortTask(tasks: List<Task>): List<Task> {
        return tasks.sortedWith(compareBy<Task> { it.dueDate }.thenByDescending { it.priority })
    }
}

