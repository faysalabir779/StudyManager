package com.example.studymanager.presentation.subject

import androidx.compose.ui.graphics.Color
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task

data class SubjectStates(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColor: List<Color> = Subject.subjectCardColor.random(),
    val studiedHours: Float = 0f,
    val progress: Float = 0f,
    val recentSession: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Session? = null,
    val isLoading: Boolean = false
)