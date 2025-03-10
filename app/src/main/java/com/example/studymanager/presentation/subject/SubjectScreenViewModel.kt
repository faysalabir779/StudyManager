package com.example.studymanager.presentation.subject

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.doamin.repository.SessionRepository
import com.example.studymanager.doamin.repository.SubjectRepository
import com.example.studymanager.doamin.repository.TaskRepository
import com.example.studymanager.presentation.navArgs
import com.example.studymanager.util.SnackBarEvent
import com.example.studymanager.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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

    //snackbar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: SubjectEvents) {
        when (event) {
            is SubjectEvents.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColor = event.color
                    )
                }
            }

            is SubjectEvents.OnSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.name
                    )
                }
            }

            is SubjectEvents.OnGoalStudyHourChange -> {
                _state.update {
                    it.copy(
                        goalStudyHours = event.hour
                    )
                }
            }

            SubjectEvents.UpdateSubject -> updateSubject()
            SubjectEvents.DeleteSubject -> deleteSubject()
            is SubjectEvents.OnTaskIsCompleteChange -> {
                updateTask(task = event.task)
            }
            SubjectEvents.DeleteSession -> deleteSubject()
            is SubjectEvents.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            SubjectEvents.UpdateProgress -> {
                val goalStudyHour = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studiedHours / goalStudyHour).coerceIn(0f, 1f)
                    )
                }
            }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.upsertTask(
                    task = task.copy(isCompleted = !task.isCompleted)
                )
                if (task.isCompleted) {
                    _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Saved in Upcoming Task"))
                } else {
                    _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Saved in completed Task"))
                }
            }catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }

        }
    }

    private fun fetchSubject() {
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let { subject ->
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

    private fun updateSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        subjectId = state.value.currentSubjectId,
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        color = state.value.subjectCardColor.map { it.toArgb() }
                    )
                )
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Subject Updated successfully"))
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSubject() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                state.value.currentSubjectId?.let {
                    subjectRepository.deleteSubject(subjectId = it)
                }
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Subject Deleted successfully"))
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    sessionRepository.deleteSession(it)
                }
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Session Deleted successfully"))
            }catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete session. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }
}