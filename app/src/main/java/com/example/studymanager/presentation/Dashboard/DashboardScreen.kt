package com.example.studymanager.presentation.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studymanager.R
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.presentation.components.AddSubjectDialogue
import com.example.studymanager.presentation.components.CountCard
import com.example.studymanager.presentation.components.DeleteDialogue
import com.example.studymanager.presentation.components.StudySessionList
import com.example.studymanager.presentation.components.SubjectCard
import com.example.studymanager.presentation.components.TaskList
import com.example.studymanager.presentation.destinations.SessionScreenRouteDestination
import com.example.studymanager.presentation.destinations.SubjectScreenRouteDestination
import com.example.studymanager.presentation.destinations.TaskScreenRouteDestination
import com.example.studymanager.presentation.session.StudySessionTimerService
import com.example.studymanager.presentation.subject.SubjectScreenNavArgs
import com.example.studymanager.presentation.task.TaskScreenNavArgs
import com.example.studymanager.util.SnackBarEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination()
@Composable
fun DashBoardScreenRoute(
    navigator: DestinationsNavigator,
    timerService: StudySessionTimerService,
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val task by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSession by viewModel.recentSession.collectAsStateWithLifecycle()

    DashboardScreen(
        onSubjectCardClick = { subjectId ->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick = { taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        },
        snackBarEvent = viewModel.snackBarEventFlow,
        state = state,
        onEvent = viewModel::onEvent,
        task = task,
        session = recentSession,
        timerService = timerService
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    state: DashboardState,
    timerService: StudySessionTimerService,
    task: List<Task>,
    session: List<Session>,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionButtonClick: () -> Unit,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (DashboardEvents) -> Unit
) {

    var isAddSubjectDialogueOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogueOpen by rememberSaveable { mutableStateOf(false) }

    val seconds by timerService.seconds

    //this is the functionality of SnackBar(Like Toast)
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        snackBarEvent.collectLatest { event ->
            when (event) {
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackBarEvent.NavigateUp -> {}
            }
        }
    }

    AddSubjectDialogue(
        isOpen = isAddSubjectDialogueOpen,
        onDismissClick = { isAddSubjectDialogueOpen = false },
        subjectName = state.subjectName,
        onSubjectChange = { onEvent(DashboardEvents.onSubjectNameChange(name = it)) },
        goalHours = state.goalStudyHours,
        onGoalHoursChange = { onEvent(DashboardEvents.onGoalStudyHourChange(hour = it)) },
        selectedColor = state.subjectCardColor,
        onColorChange = { onEvent(DashboardEvents.onSubjectCardColorChange(colors = it)) },
        onConfirmButtonClick = {
            onEvent(DashboardEvents.SaveSubject)
            isAddSubjectDialogueOpen = false
        }
    )

    DeleteDialogue(
        isOpen = isDeleteDialogueOpen,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be removed. This action cannot be undone.",
        onDismissClick = { isDeleteDialogueOpen = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvents.DeleteSession)
            isDeleteDialogueOpen = false
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "StudyManager",
                    style = MaterialTheme.typography.headlineMedium
                )
            })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardSection(
                    subjectCount = state.totalSubjectCount.toString(),
                    studiedHours = state.totalStudiedHours.toString(),
                    goalStudyHours = state.totalGoalStudyHours.toString()
                )
            }
            item {
                SubjectCardSection(
                    subjectList = state.subjects,
                    onAddIconClick = { isAddSubjectDialogueOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
            item {
                Button(
                    onClick = onStartSessionButtonClick,
                    enabled = state.subjects.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .padding(horizontal = 12.dp, vertical = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (seconds != "00") Color.Red else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = if (seconds != "00") "Check Running Study Session" else "Start Study Session")
                    Spacer(modifier = Modifier.width(25.dp))
                    if (seconds != "00"){
                        Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    }
                }
            }
            TaskList(
                sectionTile = "UPCOMING TASKS",
                note = "You don't have any upcoming tasks\n Click on + to add upcoming tasks",
                task = task,
                onCheckBoxClick = { onEvent(DashboardEvents.onTaskIsCompleteChange(it)) },
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
            StudySessionList(
                sectionTile = "SESSION LIST",
                note = "You don't have any recent study sessions\n Start a new session to track your progress",
                session = session,
                onDeleteIconClick = {
                    onEvent(DashboardEvents.onDeleteSessionButtonClick(session = it))
                    isDeleteDialogueOpen = true
                }
            )

        }

    }
}


@Composable
fun CountCardSection(
    subjectCount: String,
    studiedHours: String,
    goalStudyHours: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = subjectCount
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalStudyHours
        )
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject>,
    onAddIconClick: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp)
            )
            IconButton(onClick = onAddIconClick) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
        if (subjectList.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.books),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "No Subject Added\n Click on + to add subject",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
            ) {
                items(subjectList) { subjectList ->
                    SubjectCard(
                        subjectName = subjectList.name,
                        subjectColor = subjectList.color.map { Color(it) },
                        onClick = { onSubjectCardClick(subjectList.subjectId) })
                }
            }
        }

    }

}

