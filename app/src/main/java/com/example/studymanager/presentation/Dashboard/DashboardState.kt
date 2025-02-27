package com.example.studymanager.presentation.Dashboard

import androidx.compose.ui.graphics.Color
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject

data class DashboardState (
    val totalSubjectCount: Int = 0,
    val totalStudiedHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,

    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColor: List<Color> = Subject.subjectCardColor.random(),
    val session: Session? = null

)