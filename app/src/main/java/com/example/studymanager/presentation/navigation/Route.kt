package com.example.studymanager.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object DashBoardScreenRoute

@Serializable
data class SubjectScreenRoute(
    val subjectId: Int?
)

@Serializable
data class TaskScreenRoute(
    val taskId: Int?
)

@Serializable
object SessionScreenRoute