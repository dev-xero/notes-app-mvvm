package dev.xero.cleanarchitecturenotesapp.feature_note.domain.use_case

import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class GetNote(
	private val repository: NoteRepository
) {

	suspend operator fun invoke(id: Int): Note? {
		return repository.getNoteById(id)
	}
}