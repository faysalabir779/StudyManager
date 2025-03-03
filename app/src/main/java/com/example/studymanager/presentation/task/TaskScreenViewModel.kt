package com.example.studymanager.presentation.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymanager.doamin.model.Task
import com.example.studymanager.doamin.repository.SubjectRepository
import com.example.studymanager.doamin.repository.TaskRepository
import com.example.studymanager.presentation.navArgs
import com.example.studymanager.util.Priority
import com.example.studymanager.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val subjectRepository: SubjectRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()
    private val _state = MutableStateFlow(TaskStates())
    val state = combine(
        _state,
        subjectRepository.getAllSubject()
    ) { state, subjects ->
        state.copy(
            subjects = subjects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskStates()
    )

    //snackbar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        fetchTask()
        fetchSubject()
    }

    fun onEvent(events: TaskEvents) {
        when (events) {
            is TaskEvents.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = events.title
                    )
                }
            }

            is TaskEvents.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = events.description
                    )
                }
            }

            is TaskEvents.OnDueDateChange -> {
                _state.update {
                    it.copy(
                        dueDate = events.millis
                    )
                }
            }

            is TaskEvents.OnPriorityChange -> {
                _state.update {
                    it.copy(
                        priority = events.priority
                    )
                }
            }

            TaskEvents.OnIsCompleteChange -> {
                _state.update {
                    it.copy(
                        isTaskComplete = !state.value.isTaskComplete
                    )
                }
            }

            is TaskEvents.OnRelatedToSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = events.subject.name,
                        subjectId = events.subject.subjectId
                    )
                }
            }

            TaskEvents.SaveTask -> saveTask()
            TaskEvents.DeleteTask -> deleteTask()
        }
    }

    private fun deleteTask () {
        viewModelScope.launch {
            try {
                val currentTaskId = state.value.currentTaskId
                if (currentTaskId != null){
                    withContext(Dispatchers.IO){
                        taskRepository.deleteTaskId(taskId = currentTaskId)
                    }
                    _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Task Deleted successfully"))
                    _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
                }
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete Task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            val state = _state.value
            if (state.subjectId == null || state.relatedToSubject == null) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Please Select Subject Related to Task.",

                        )
                )
                return@launch
            }
            try {
                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate ?: Instant.now().toEpochMilli(),
                        relatedToSubject = state.relatedToSubject,
                        priority = state.priority.value,
                        isCompleted = state.isTaskComplete,
                        taskSubjectId = state.subjectId,
                        taskId = state.currentTaskId

                    )
                )
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Task Added successfully"))
                _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't add task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }

        }
    }

    private fun fetchTask() {
        viewModelScope.launch {
            navArgs.taskId?.let {id->
                taskRepository.getTaskById(id)?.let { task->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isCompleted,
                            relatedToSubject = task.relatedToSubject,
                            priority = Priority.fromValue(task.priority),
                            subjectId = task.taskSubjectId,
                            currentTaskId = task.taskId
                        )
                    }

                }
            }

        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            navArgs.subjectId?.let {id->
                subjectRepository.getSubjectById(id)?.let { subject->
                    _state.update {
                        it.copy(
                            subjectId = subject.subjectId,
                            relatedToSubject = subject.name
                        )
                    }

                }
            }

        }
    }
}
