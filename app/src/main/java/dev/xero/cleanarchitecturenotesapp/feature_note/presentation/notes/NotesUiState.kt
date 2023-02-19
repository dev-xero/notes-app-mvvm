package dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes

import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.OrderType

data class NotesUiState(
	val notes: List<Note> = emptyList(),
	val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
	val isOrderSectionVisible: Boolean = false
)