package com.example.studymanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.studymanager.presentation.Dashboard.DashboardScreen
import com.example.studymanager.presentation.Dashboard.DashboardViewModel
import com.example.studymanager.presentation.session.SessionScreen
import com.example.studymanager.presentation.session.SessionScreenViewModel
import com.example.studymanager.presentation.subject.SubjectScreen
import com.example.studymanager.presentation.subject.SubjectScreenViewModel
import com.example.studymanager.presentation.task.TaskScreen
import com.example.studymanager.presentation.task.TaskScreenViewModel

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val sessionViewModel: SessionScreenViewModel = hiltViewModel()
    val taskViewModel: TaskScreenViewModel = hiltViewModel()
    val subjectViewModel: SubjectScreenViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = DashBoardScreenRoute) {
        composable<DashBoardScreenRoute> {
            DashboardScreen(
                navController,
                dashboardViewModel,
                dashboardViewModel::onEvent
            )
        }
        composable<SubjectScreenRoute> {
            val subjectId = it.toRoute<SubjectScreenRoute>()
            SubjectScreen(navController, subjectId.subjectId)
        }
        composable<TaskScreenRoute> { TaskScreen(navController) }
        composable<SessionScreenRoute> { SessionScreen(navController) }
    }
}