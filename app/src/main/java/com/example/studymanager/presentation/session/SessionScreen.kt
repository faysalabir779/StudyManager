package com.example.studymanager.presentation.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studymanager.presentation.components.DeleteDialogue
import com.example.studymanager.presentation.components.StudySessionList
import com.example.studymanager.presentation.components.SubjectListBottomSheet
import com.example.studymanager.presentation.components.TaskCheckBox
import com.example.studymanager.session
import com.example.studymanager.subjects
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isSubjectBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

    var isTaskDelete by rememberSaveable { mutableStateOf(false) }


    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isSubjectBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isSubjectBottomSheetOpen = false
            }
        },
        onDismissRequest = { isSubjectBottomSheetOpen = false }
    )

    DeleteDialogue(isOpen = isTaskDelete,
        title = "Delete Study Session?",
        bodyText = "Are you sure, you want to delete this task? This action cannot be undone.",
        onDismissClick = { isTaskDelete = false },
        onConfirmButtonClick = { isTaskDelete = false })

    Scaffold(topBar = {
        SessionScreenTopBar(onBackClick = {})
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                TimerSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubjectSection(isOpen = { isSubjectBottomSheetOpen = true })
            }
            item {
                ButtonSection(startButton = { }, cancelButton = { }, finishButton = {})
            }
            StudySessionList(sectionTile = "STUDY SESSIONS HISTORY",
                note = "You don't have any recent study sessions\n Start a new session to track your progress",
                session = session,
                onDeleteIconClick = {isTaskDelete = true})

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopBar(
    onBackClick: () -> Unit,
) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
    }, title = {
        Text(text = "Study Sessions", style = MaterialTheme.typography.headlineSmall)
    })
}

@Composable
private fun TimerSection(modifier: Modifier) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(text = "00:05:59", style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp))
    }
}

@Composable
private fun RelatedToSubjectSection(isOpen: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(text = "Related to subject", style = MaterialTheme.typography.bodySmall)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "English", style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = isOpen) {
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ButtonSection(
    modifier: Modifier = Modifier,
    startButton: () -> Unit,
    cancelButton: () -> Unit,
    finishButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = cancelButton) {
            Text(text = "Cancel", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }
        Button(onClick = startButton) {
            Text(text = "Start", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }
        Button(onClick = finishButton) {
            Text(text = "Finish", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
        }

    }

}

