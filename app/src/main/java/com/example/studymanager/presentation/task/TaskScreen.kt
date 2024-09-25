package com.example.studymanager.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studymanager.presentation.components.DatePicker
import com.example.studymanager.presentation.components.DeleteDialogue
import com.example.studymanager.presentation.components.SubjectListBottomSheet

import com.example.studymanager.presentation.components.TaskCheckBox
import com.example.studymanager.subjects
import com.example.studymanager.util.Common
import com.example.studymanager.util.changeMillisToDateString
import kotlinx.coroutines.launch
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier) {

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    var titleError by rememberSaveable { mutableStateOf<String?>(null) }

    var isTaskDelete by rememberSaveable { mutableStateOf(false) }
    var isDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    //For BottomSheet
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isSubjectBottomSheetOpen by rememberSaveable { mutableStateOf(false) }


    titleError = when {
        title.isBlank() -> "Please Enter Task Title"
        title.length < 4 -> "Task title is too short"
        title.length > 30 -> "Task title is too long"
        else -> null
    }

    DeleteDialogue(isOpen = isTaskDelete,
        title = "Delete Task?",
        bodyText = "Are you sure, you want to delete this task? This action cannot be undone.",
        onDismissClick = { isTaskDelete = false },
        onConfirmButtonClick = { isTaskDelete = false })

    DatePicker(state = datePickerState,
        isOpen = isDatePicker,
        onDismissRequest = { isDatePicker = false },
        onConfirmClick = { isDatePicker = false })

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isSubjectBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible)isSubjectBottomSheetOpen = false
            }
        },
        onDismissRequest = { isSubjectBottomSheetOpen = false }
    )


    Scaffold(topBar = {
        TaskScreenTopBar(isTaskExist = true,
            isComplete = false,
            onBackClick = {},
            checkBoxBorderColor = Color.Red,
            onDeleteButtonClick = { isTaskDelete = true },
            onCheckBoxClick = {})
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(text = "Title")
                },
                supportingText = {
                    Text(text = titleError.orEmpty())
                })
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Description")
                })
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.bodySmall)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = { isDatePicker = true }) {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Priority", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Common.entries.forEach { priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        bgColor = priority.color,
                        borderColor = if (priority == Common.HIGH) Color.White else Color.Transparent,
                        labelColor = if (priority == Common.HIGH) Color.White else Color.White.copy(
                            alpha = 0.7f
                        )
                    ) {

                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Related to subject", style = MaterialTheme.typography.bodySmall)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "English", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { isSubjectBottomSheetOpen = true }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
                }
            }

            Button(
                enabled = titleError == null,
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    onBackClick: () -> Unit,
    checkBoxBorderColor: Color,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit

) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
    }, title = {
        Text(text = "Task", style = MaterialTheme.typography.headlineSmall)
    }, actions = {
        if (isTaskExist) {
            TaskCheckBox(
                isCompleted = isComplete,
                borderColor = checkBoxBorderColor,
                onCheckBoxClick = onCheckBoxClick
            )
            IconButton(onClick = onDeleteButtonClick) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }
    })
}

@Composable
fun PriorityButton(
    modifier: Modifier,
    label: String,
    bgColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .background(bgColor)
        .clickable { onClick }
        .padding(5.dp)
        .border(1.dp, borderColor, RoundedCornerShape(5.dp))
        .padding(5.dp),
        contentAlignment = Alignment.Center) {
        Text(text = label, color = labelColor)

    }
}