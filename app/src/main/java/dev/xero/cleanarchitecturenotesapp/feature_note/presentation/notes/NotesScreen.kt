package dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.xero.cleanarchitecturenotesapp.R
import dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes.components.NoteItem
import dev.xero.cleanarchitecturenotesapp.feature_note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel()
) {
	val state = viewModel.uiState.value
	val scaffoldState = rememberScaffoldState()
	val scope = rememberCoroutineScope()

	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = { /*TODO*/ },
				backgroundColor = MaterialTheme.colors.primary
			) {
				Icon(
					imageVector = Icons.Default.Add,
					contentDescription = null
				)
			}
		},
		scaffoldState = scaffoldState
	) {
		padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.your_note),
					style = MaterialTheme.typography.h4
				)

				IconButton(onClick = {
					viewModel.onEvent(NotesEvent.ToggleOrderSection)
				}) {
					Icon(
						imageVector = Icons.Default.Sort,
						contentDescription = null
					)
				}
			}

			AnimatedVisibility(
				visible = state.isOrderSectionVisible,
				enter = fadeIn() + slideInVertically(),
				exit = fadeOut() + slideOutVertically()
			) {
				OrderSection(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 16.dp),
					onOrderChange = {
						viewModel.onEvent(NotesEvent.Order(it))
					}
				)
			}
			Spacer(modifier = Modifier.height(16.dp))
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.notes) { note ->
					NoteItem(
						note = note,
						modifier = Modifier
							.fillMaxWidth()
							.clickable {

							},
							onDeleteClick = {
								viewModel.onEvent(NotesEvent.DeleteNote(note))
								scope.launch {
									val result = scaffoldState.snackbarHostState.showSnackbar(
										message = "Note deleted",
										actionLabel = "Undo"
									)

									if (result == SnackbarResult.ActionPerformed) {
										viewModel.onEvent(NotesEvent.RestoreNote)
									}
								}
							}
						)
					}
				}
			}
		}
	}
}