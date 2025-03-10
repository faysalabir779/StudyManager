package com.example.studymanager.presentation.session

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.repository.SessionRepository
import com.example.studymanager.doamin.repository.SubjectRepository
import com.example.studymanager.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(SessionState())
    val state = combine(
        _state,
        subjectRepository.getAllSubject(),
        sessionRepository.getAllSession()
    ) { state, subject, session ->
        state.copy(
            sessions = session,
            subjects = subject
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionState()
    )

    //snackbar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(events: SessionEvents) {
        when (events) {
            SessionEvents.NotifySubjectId -> notifyToUpdateSubject()

            SessionEvents.DeleteSession -> deleteSession()

            is SessionEvents.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = events.session
                    )
                }
            }

            is SessionEvents.OnRelatedSubjectChange -> {
                _state.update {
                    it.copy(
                        relatedToSubject = events.subject.name,
                        subjectId = events.subject.subjectId
                    )
                }
            }

            is SessionEvents.SaveSession -> insertSession(events.duration)

            is SessionEvents.UpdateSubjectIdAndRelatedSubject -> {
                _state.update {
                    it.copy(
                        relatedToSubject = events.relatedToSubject,
                        subjectId = events.subjectId
                    )
                }
            }
        }
    }

    private fun notifyToUpdateSubject() {
        viewModelScope.launch {
            if (state.value.relatedToSubject == null || state.value.subjectId == null) {
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Please select the subject related to the session"))
            }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    sessionRepository.deleteSession(it)
                }
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Session Deleted successfully"))
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete session. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun insertSession(duration: Long) {
        viewModelScope.launch {
            if (duration < 36) {
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Single session cannot be less than 36 seconds"))
                return@launch
            }
            try {
                sessionRepository.insertSession(
                    session = Session(
                        sessionSubjectId = state.value.subjectId ?: -1,
                        relatedToSubject = state.value.relatedToSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration,
                    )
                )
                _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar(message = "Session saved successfully"))
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't save session. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }

    }
}