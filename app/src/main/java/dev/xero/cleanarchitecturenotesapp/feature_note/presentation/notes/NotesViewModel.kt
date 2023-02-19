package dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.use_case.NoteUseCases
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val noteUseCases: NoteUseCases
) : ViewModel() {

	private val _uiState = mutableStateOf<NotesUiState>(NotesUiState())
	val uiState: State<NotesUiState> = _uiState

	private var recentlyDeletedNote: Note? = null

	private var getNotesJob: Job? = null

	init {
		getNotes(NoteOrder.Date(OrderType.Descending))
	}

	fun onEvent(event: NotesEvent) {
		when(event) {
			is NotesEvent.Order -> {
				if (
					uiState.value.noteOrder::class == event.noteOrder::class &&
					uiState.value.noteOrder.orderType == event.noteOrder.orderType
				) {
					return
				}
				getNotes(event.noteOrder)
			}

			is NotesEvent.DeleteNote -> {
				viewModelScope.launch {
					noteUseCases.deleteNote(event.note)
					recentlyDeletedNote = event.note
				}
			}

			is NotesEvent.RestoreNote -> {
				viewModelScope.launch {
					noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
					recentlyDeletedNote = null
				}
			}

			is NotesEvent.ToggleOrderSection -> {
				_uiState.value = uiState.value.copy(
					isOrderSectionVisible = !uiState.value.isOrderSectionVisible
				)
			}
		}
	}

	private fun getNotes(notesOrder: NoteOrder) {
		getNotesJob?.cancel()

		getNotesJob = noteUseCases.getNotes(notesOrder)
			.onEach {
				notes -> _uiState.value = uiState.value.copy(
					notes = notes,
					noteOrder = notesOrder
				)
			}
			.launchIn(viewModelScope)
	}
}