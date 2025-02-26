package com.example.studymanager.doamin.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null,
    val taskSubjectId: Int

)
