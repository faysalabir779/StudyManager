package com.example.studymanager.presentation.task

import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.util.Priority

sealed class TaskEvents {
    data class OnTitleChange(val title: String): TaskEvents()

    data class OnDescriptionChange(val description: String): TaskEvents()

    data class OnDueDateChange(val millis: Long?): TaskEvents()

    data class OnPriorityChange(val priority: Priority): TaskEvents()

    data class OnRelatedToSubjectSelect(val subject: Subject): TaskEvents()

    data object OnIsCompleteChange: TaskEvents()

    data object SaveTask: TaskEvents()

    data object DeleteTask: TaskEvents()

}