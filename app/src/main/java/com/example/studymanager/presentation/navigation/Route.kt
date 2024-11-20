package com.example.studymanager.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object DashBoardScreenRoute

@Serializable
object SubjectScreenRoute

@Serializable
data class TaskScreenRoute(
    val taskId: Int?
)

@Serializable
object SessionScreenRoute