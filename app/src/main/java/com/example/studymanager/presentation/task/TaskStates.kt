package com.example.studymanager.presentation.task

import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.util.Priority


data class TaskStates(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority = Priority.LOW,
    val relatedToSubject: String? = null,
    val subjects: List<Subject> = emptyList(),
    val subjectId: Int? = null,
    val currentTaskId: Int? = null
)
