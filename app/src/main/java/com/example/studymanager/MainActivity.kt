package com.example.studymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.presentation.Dashboard.DashboardScreen
import com.example.studymanager.presentation.subject.SubjectScreen
import com.example.studymanager.presentation.task.TaskScreen
import com.example.studymanager.presentation.theme.StudyManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SubjectScreen()
                }
            }
        }
    }
}

val subjects = listOf(
    Subject("Maths", 10, Subject.subjectCardColor[0], subjectId = 0),
    Subject("English", 2, Subject.subjectCardColor[1], subjectId = 0),
    Subject("Biology", 3, Subject.subjectCardColor[2], subjectId = 0),
    Subject("Chemistry", 8, Subject.subjectCardColor[3], subjectId = 0)
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
        taskId = 0,
        taskSubjectId = 0

    ),
    Task(
        title = "Math Practice",
        description = "Practice solving math problems",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "Maths",
        isCompleted = false,
        taskId = 0,
        taskSubjectId = 0
    ),
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
        priority = 2,
        relatedToSubject = "Maths",
        isCompleted = true,
        taskId = 0,
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