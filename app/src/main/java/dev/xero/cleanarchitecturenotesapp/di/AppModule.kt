package dev.xero.cleanarchitecturenotesapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.xero.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDatabase
import dev.xero.cleanarchitecturenotesapp.feature_note.data.repository.NoteRepositoryImplementation
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideNoteDatabase(app: Application): NoteDatabase {
		return Room.databaseBuilder(
			app,
			NoteDatabase::class.java,
			NoteDatabase.DATABASE_NAME
		).build()
	}

	@Provides
	@Singleton
	fun provideNoteRepository(db: NoteDatabase): NoteRepository {
		return NoteRepositoryImplementation(db.noteDao)
	}

	@Provides
	@Singleton
	fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
		return NoteUseCases(
			addNote = AddNote(repository),
			getNote = GetNote(repository),
			getNotes = GetNotes(repository),
			deleteNote = DeleteNote(repository)
		)
	}
}