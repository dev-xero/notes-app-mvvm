package dev.xero.cleanarchitecturenotesapp.feature_note.data.data_source

import androidx.room.*
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

	@Query(value = "SELECT * FROM note")
	fun getNotes(): Flow<List<Note>>

	@Query(value = "SELECT * FROM note WHERE id = :id")
	suspend fun getNoteById(id: Int): Note?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertNote(note: Note)

	@Delete
	suspend fun deleteNote(note: Note)
}