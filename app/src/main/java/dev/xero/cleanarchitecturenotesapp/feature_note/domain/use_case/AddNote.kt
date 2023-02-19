package dev.xero.cleanarchitecturenotesapp.feature_note.domain.use_case

import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.InvalidNoteException
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class AddNote(
	private val repository: NoteRepository
) {

	@Throws(InvalidNoteException::class)
	suspend operator fun invoke(note: Note) {
		if (note.title.isBlank() || note.content.isBlank()) {
			throw InvalidNoteException("The title or content of the note can't be empty")
		}

		repository.insertNote(note)
	}
}