package com.example.studymanager.presentation.Dashboard

import androidx.compose.ui.graphics.Color
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Task

sealed class DashboardEvents {

    data object SaveSubject: DashboardEvents()

    data object DeleteSession: DashboardEvents()

    data class onDeleteSessionButtonClick(val session: Session): DashboardEvents()

    data class onTaskIsCompleteChange(val task: Task): DashboardEvents()

    data class onSubjectCardColorChange(val colors: List<Color>): DashboardEvents()

    data class onSubjectNameChange(val name: String): DashboardEvents()

    data class onGoalStudyHourChange(val hour: String): DashboardEvents()


}