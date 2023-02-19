package dev.xero.cleanarchitecturenotesapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.xero.cleanarchitecturenotesapp.ui.theme.*

@Entity
data class Note(
	val title: String,
	val content: String,
	val timeStamp: Long,
	val color: Int,
	@PrimaryKey val id: Int? = null
) {
	companion object {
		val noteColors = listOf<Color> (
			RedOrange,
			LightGreen,
			Violet,
			SkyBlue,
			RedPink
		)
	}
}

class InvalidNoteException(msg: String): Exception(msg)
