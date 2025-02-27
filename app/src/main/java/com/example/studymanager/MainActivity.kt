package com.example.studymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.presentation.navigation.App
import com.example.studymanager.presentation.theme.StudyManagerTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App()
                }
            }
        }
    }
}

val subjects = listOf(
    Subject(name = "Maths", 10f, color = Subject.subjectCardColor[0].map { it.toArgb() }, subjectId = 0),
    Subject(name = "English", 2f, color = Subject.subjectCardColor[1].map { it.toArgb() }, subjectId = 1),
    Subject(name = "Biology", 3f, color = Subject.subjectCardColor[2].map { it.toArgb() }, subjectId = 2),
    Subject(name = "Chemistry", 8f, color = Subject.subjectCardColor[3].map { it.toArgb() }, subjectId = 4)
)
val tasks = listOf(
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "Maths",
        isCompleted = true,
        taskId = 0,
        taskSubjectId = 0
    ),
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Maths",
        isCompleted = false,
        taskId = 1,
        taskSubjectId = 0

    ),
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "Maths",
        isCompleted = false,
        taskId = 2,
        taskSubjectId = 0
    ),
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "Maths",
        isCompleted = true,
        taskId = 3,
        taskSubjectId = 0
    ),
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "Maths",
        isCompleted = true,
        taskId = 4,
        taskSubjectId = 0
    )

)
val session = listOf(
    Session(
        sessionSubjectId = 1,
        sessionId = 1,
        relatedToSubject = "English",
        date = 0L,
        duration = 0L
    ),
    Session(
        sessionSubjectId = 1,
        sessionId = 1,
        relatedToSubject = "Bangladesh Studies",
        date = 0L,
        duration = 0L
    ),
    Session(
        sessionSubjectId = 1,
        sessionId = 1,
        relatedToSubject = "Mathmatics",
        date = 0L,
        duration = 0L
    ),
    Session(
        sessionSubjectId = 1,
        sessionId = 1,
        relatedToSubject = "English",
        date = 0L,
        duration = 0L
    ),
    Session(
        sessionSubjectId = 1,
        sessionId = 1,
        relatedToSubject = "Bangladesh Studies",
        date = 0L,
        duration = 0L
    )
)