package com.example.studymanager.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.presentation.components.AddSubjectDialogue
import com.example.studymanager.presentation.components.CountCard
import com.example.studymanager.presentation.components.DeleteDialogue
import com.example.studymanager.presentation.components.StudySessionList
import com.example.studymanager.presentation.components.TaskList
import com.example.studymanager.session
import com.example.studymanager.tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(modifier: Modifier = Modifier) {

    //for top app bar animation to single line app bar
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    //for floatingActionButton animation
    val lazyListState = rememberLazyListState()
    val isFABExpanded by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex == 0 }
    }

    var subjectName by rememberSaveable { mutableStateOf("") }
    var goalStudyHours by rememberSaveable { mutableStateOf("") }
    var selectedColor by rememberSaveable { mutableStateOf(Subject.subjectCardColor.random()) }

    var isAddSubjectDialogueOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogueOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogueOpen by rememberSaveable { mutableStateOf(false) }

    AddSubjectDialogue(
        isOpen = isAddSubjectDialogueOpen,
        onDismissClick = { isAddSubjectDialogueOpen = false },
        subjectName = subjectName,
        onSubjectChange = { subjectName = it },
        goalHours = goalStudyHours,
        onGoalHoursChange = { goalStudyHours = it },
        selectedColor = selectedColor,
        onColorChange = { selectedColor = it },
        onConfirmButtonClick = {
            isAddSubjectDialogueOpen = false
        }
    )

    //delete subject from top app bar
    DeleteDialogue(
        isOpen = isDeleteSubjectDialogueOpen,
        title = "Delete Subject?",
        bodyText = "Are you sure, you want to delete this subject? All related task and study session will be removed. This action cannot be undone.",
        onDismissClick = { isDeleteSubjectDialogueOpen = false },
        onConfirmButtonClick = { isDeleteSubjectDialogueOpen = false }
    )

    //delete session
    DeleteDialogue(
        isOpen = isDeleteDialogueOpen,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be removed. This action cannot be undone.",
        onDismissClick = { isDeleteDialogueOpen = false },
        onConfirmButtonClick = { isDeleteDialogueOpen = false }
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                onBackClick = {},
                onDeleteClick = {isDeleteSubjectDialogueOpen = true},
                onEditClick = {isAddSubjectDialogueOpen = true},
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                text = {
                    Text(text = "Add Task")
                },
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                },
                expanded = isFABExpanded
            )
        }) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                SubjectOverViewSection(
                    modifier = Modifier.padding(12.dp),
                    studiedHours = "10",
                    goalHours = "12",
                    progress = 0.6f
                )
            }
            TaskList(sectionTile = "UPCOMING TASKS",
                note = "You don't have any upcoming tasks\n Click on + to add upcoming tasks",
                task = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {})
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
            TaskList(sectionTile = "COMPLETED TASKS",
                note = "You don't have any completed tasks\n Click on the check box to mark as completed",
                task = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {})
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
            StudySessionList(sectionTile = "SESSION LIST",
                note = "You don't have any recent study sessions\n Start a new session to track your progress",
                session = session,
                onDeleteIconClick = { isDeleteDialogueOpen = true})

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopBar(
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }, title = {
            Text(
                text = "Subject",
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }, actions = {
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
            IconButton(onClick = onEditClick) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
        })
}

@Composable
fun SubjectOverViewSection(
    modifier: Modifier, studiedHours: String, goalHours: String, progress: Float
) {
    val percentageProgress = remember(progress) { (progress * 100).toInt().coerceIn(0, 100) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f), headingText = "Goal Study Hours", count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f), headingText = "Studied Hours", count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.size(75.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 5.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 5.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress%")
        }
    }
}