package com.example.studymanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.studymanager.presentation.Dashboard.DashboardScreen
import com.example.studymanager.presentation.session.SessionScreen
import com.example.studymanager.presentation.subject.SubjectScreen
import com.example.studymanager.presentation.task.TaskScreen

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = DashBoardScreenRoute) {
        composable<DashBoardScreenRoute> { DashboardScreen(navController) }
        composable<SubjectScreenRoute> {
            val subjectId = it.toRoute<SubjectScreenRoute>()
            SubjectScreen(navController, subjectId.subjectId)
        }
        composable<TaskScreenRoute> { TaskScreen(navController) }
        composable<SessionScreenRoute> { SessionScreen(navController) }
    }
}