package com.example.studymanager.presentation.subject

import androidx.compose.ui.graphics.Color
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Task

sealed class SubjectEvents {

    data object UpdateSubject: SubjectEvents()

    data object DeleteSubject: SubjectEvents()
    
    data object DeleteSession: SubjectEvents()

    data class OnTaskIsCompleteChange(val task: Task): SubjectEvents()

    data class OnSubjectCardColorChange(val color: List<Color>): SubjectEvents()

    data class OnSubjectNameChange(val name: String): SubjectEvents()

    data class OnGoalStudyHourChange(val hour: String): SubjectEvents()

    data class OnDeleteSessionButtonClick(val session: Session): SubjectEvents()

}