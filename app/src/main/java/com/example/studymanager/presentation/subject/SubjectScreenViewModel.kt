package com.example.studymanager.presentation.subject

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymanager.doamin.repository.SessionRepository
import com.example.studymanager.doamin.repository.SubjectRepository
import com.example.studymanager.doamin.repository.TaskRepository
import com.example.studymanager.presentation.navArgs
import com.example.studymanager.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {



    private val navArgs: SubjectScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(SubjectStates())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(navArgs.subjectId),
        taskRepository.getCompletedTaskForSubject(navArgs.subjectId),
        sessionRepository.getRecentTenSessionForSubject(navArgs.subjectId),
        sessionRepository.getTotalSessionDurationBySubject(navArgs.subjectId)
    ) { state, upcomingTasks, completedTasks, sessions, totalSessionDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,
            completedTasks = completedTasks,
            recentSession = sessions,
            studiedHours = totalSessionDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectStates()
    )
    init {
        fetchSubject()
    }

    fun onEvent(event: SubjectEvents) {
        when (event) {
            SubjectEvents.DeleteSession -> TODO()
            SubjectEvents.DeleteSubject -> TODO()
            is SubjectEvents.OnDeleteSessionButtonClick -> TODO()
            is SubjectEvents.OnGoalStudyHourChange -> TODO()
            is SubjectEvents.OnSubjectCardColorChange -> TODO()
            is SubjectEvents.OnSubjectNameChange -> TODO()
            is SubjectEvents.OnTaskIsCompleteChange -> TODO()
            SubjectEvents.UpdateSubject -> TODO()
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let {subject->
                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColor = subject.color.map { Color((it)) },
                        currentSubjectId = subject.subjectId
                    )
                }
            }
        }
    }
}