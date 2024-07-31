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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.example.studymanager.session
import com.example.studymanager.subjects
import com.example.studymanager.tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {


    var subjectName by rememberSaveable { mutableStateOf("") }
    var goalStudyHours by rememberSaveable { mutableStateOf("") }
    var selectedColor by rememberSaveable { mutableStateOf(Subject.subjectCardColor.random()) }

    var isAddSubjectDialogueOpen by rememberSaveable { mutableStateOf(false) }
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

    DeleteDialogue(
        isOpen = isDeleteDialogueOpen,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be removed. This action cannot be undone.",
        onDismissClick = { isDeleteDialogueOpen = false },
        onConfirmButtonClick = { isDeleteDialogueOpen = false }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "StudyManager",
                    style = MaterialTheme.typography.headlineMedium
                )
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                CountCardSection(subjectCount = "5", studiedHours = "10", goalStudyHours = "12")
            }
            item {
                SubjectCardSection(
                    subjectList = subjects,
                    onAddIconClick = { isAddSubjectDialogueOpen = true })
            }
            item {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .padding(horizontal = 12.dp, vertical = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Start Study Session")
                }
            }
            TaskList(
                sectionTile = "UPCOMING TASKS",
                note = "You don't have any upcoming tasks\n Click on + to add upcoming tasks",
                task = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
            StudySessionList(
                sectionTile = "SESSION LIST",
                note = "You don't have any recent study sessions\n Start a new session to track your progress",
                session = session,
                onDeleteIconClick = { isDeleteDialogueOpen = true }
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
    onAddIconClick: () -> Unit
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
                        subjectColor = subjectList.color,
                        onClick = {})
                }
            }
        }

    }

}

