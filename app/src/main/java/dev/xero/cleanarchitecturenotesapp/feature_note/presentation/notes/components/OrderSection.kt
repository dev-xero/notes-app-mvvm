package dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.xero.cleanarchitecturenotesapp.R
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import dev.xero.cleanarchitecturenotesapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
	modifier: Modifier = Modifier,
	noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
	onOrderChange: (NoteOrder) -> Unit
) {
	Column(
		modifier = modifier
	) {
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			// TITLE ORDER BUTTON
			DefaultRadioButton(
				text = stringResource(R.string.order_title),
				selected = noteOrder is NoteOrder.Title,
				onSelect = {
					onOrderChange(NoteOrder.Title(noteOrder.orderType))
				}
			)
			Spacer(modifier = Modifier.width(8.dp))

			// DATE ORDER BUTTON
			DefaultRadioButton(
				text = stringResource(R.string.order_date),
				selected = noteOrder is NoteOrder.Title,
				onSelect = {
					onOrderChange(NoteOrder.Date(noteOrder.orderType))
				}
			)
			Spacer(modifier = Modifier.width(8.dp))

			// COLOR ORDER BUTTON
			DefaultRadioButton(
				text = stringResource(R.string.order_color),
				selected = noteOrder is NoteOrder.Title,
				onSelect = {
					onOrderChange(NoteOrder.Color(noteOrder.orderType))
				}
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		Row(modifier = Modifier.fillMaxWidth()) {
			// ASCENDING ORDER BUTTON
			DefaultRadioButton(
				text = stringResource(R.string.order_ascending),
				selected = noteOrder.orderType is OrderType.Ascending,
				onSelect = {
					onOrderChange(noteOrder.copy(
						OrderType.Ascending
					))
				}
			)
			Spacer(modifier = Modifier.width(8.dp))

			// DESCENDING ORDER BUTTON
			DefaultRadioButton(
				text = stringResource(R.string.order_descending),
				selected = noteOrder.orderType is OrderType.Descending,
				onSelect = {
					onOrderChange(noteOrder.copy(
						OrderType.Descending
					))
				}
			)
		}
	}
}