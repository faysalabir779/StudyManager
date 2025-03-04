package com.example.studymanager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.presentation.NavGraphs
import com.example.studymanager.presentation.destinations.SessionScreenRouteDestination
import com.example.studymanager.presentation.session.StudySessionTimerService
import com.example.studymanager.presentation.theme.StudyManagerTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as StudySessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }

    }

    override fun onStart() {
        super.onStart()
        Intent(this, StudySessionTimerService::class.java).also {intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (isBound) {
                StudyManagerTheme {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(SessionScreenRouteDestination) { timerService }
                        }
                    )
                }
            }
        }
        requestPermission()
    }

    //post notifications permission
    //step - 2 (step - 1 in the Manifest for user permission)
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}


val subjects = listOf(
    Subject(
        name = "Maths",
        10f,
        color = Subject.subjectCardColor[0].map { it.toArgb() },
        subjectId = 0
    ),
    Subject(
        name = "English",
        2f,
        color = Subject.subjectCardColor[1].map { it.toArgb() },
        subjectId = 1
    ),
    Subject(
        name = "Biology",
        3f,
        color = Subject.subjectCardColor[2].map { it.toArgb() },
        subjectId = 2
    ),
    Subject(
        name = "Chemistry",
        8f,
        color = Subject.subjectCardColor[3].map { it.toArgb() },
        subjectId = 4
    )
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