package com.example.studymanager.doamin.model

import androidx.compose.ui.graphics.Color
import com.example.studymanager.presentation.theme.gradient1
import com.example.studymanager.presentation.theme.gradient2
import com.example.studymanager.presentation.theme.gradient3
import com.example.studymanager.presentation.theme.gradient4
import com.example.studymanager.presentation.theme.gradient5

data class Subject(
    val name: String,
    val studiedHours: Int,
    val color: List<Color>,
    val subjectId: Int
) {
    companion object {
        val subjectCardColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
