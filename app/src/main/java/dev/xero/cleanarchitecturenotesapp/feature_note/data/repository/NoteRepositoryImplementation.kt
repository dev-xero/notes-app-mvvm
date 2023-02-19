package dev.xero.cleanarchitecturenotesapp.feature_note.data.repository

import dev.xero.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDao
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation (
	private val dao: NoteDao
): NoteRepository {

	override fun getNotes(): Flow<List<Note>> {
		return dao.getNotes()
	}

	override suspend fun getNoteById(id: Int): Note? {
		return dao.getNoteById(id = id)
	}

	override suspend fun insertNote(note: Note) {
		dao.insertNote(note = note)
	}

	override suspend fun deleteNote(note: Note) {
		dao.deleteNote(note = note)
	}
}