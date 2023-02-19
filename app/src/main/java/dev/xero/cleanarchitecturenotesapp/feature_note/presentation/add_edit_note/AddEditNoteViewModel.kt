package dev.xero.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.InvalidNoteException
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.use_case.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
	private val noteUseCases: NoteUseCases
): ViewModel() {

	private val _noteTitle = mutableStateOf(NoteTextFieldState(
		hint = "Title..."
	))
	val noteTitle: State<NoteTextFieldState> = _noteTitle

	private val _noteContent = mutableStateOf(NoteTextFieldState(
		hint = "Content"
	))
	val noteContent: State<NoteTextFieldState> = _noteContent

	private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
	val noteColor: State<Int> = _noteColor

	private val _eventFlow = MutableSharedFlow<UiEvent>()
	val eventFlow = _eventFlow.asSharedFlow()

	private var currentNoteId: Int? = null

	fun onEvent(event: AddEditNoteEvent) {
		when(event) {
			// TITLE
			is AddEditNoteEvent.EnteredTitle -> {
				_noteTitle.value = noteTitle.value.copy(
					text = event.value
				)
			}

			is AddEditNoteEvent.ChangeTitleFocus -> {
				_noteTitle.value = noteTitle.value.copy(
					isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
				)
			}

			// CONTENT
			is AddEditNoteEvent.EnteredContent -> {
				_noteContent.value = noteContent.value.copy(
					text = event.value
				)
			}

			is AddEditNoteEvent.ChangeContentFocus -> {
				_noteContent.value = noteContent.value.copy(
					isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
				)
			}

			is AddEditNoteEvent.ChangeColor -> {
				_noteColor.value = event.color
			}

			is AddEditNoteEvent.SaveNote -> {
				viewModelScope.launch {
					try {
						noteUseCases.addNote(
							Note(
								id = currentNoteId,
								title = noteTitle.value.text,
								content = noteContent.value.text,
								color = noteColor.value,
								timeStamp = System.currentTimeMillis()
							)
						)
						_eventFlow.emit(UiEvent.SaveNote)
					} catch(e: InvalidNoteException) {
						_eventFlow.emit(
							UiEvent.ShowSnackbar(
								message = e.message ?: "Couldn't save note"
							)
						)
					}
				}
			}
		}
	}

	sealed class UiEvent {
		data class ShowSnackbar(val message: String): UiEvent()
		object SaveNote: UiEvent()
	}
}