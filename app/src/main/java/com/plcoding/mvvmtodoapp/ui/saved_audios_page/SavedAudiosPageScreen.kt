package com.plcoding.mvvmtodoapp.ui.saved_audios_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SavedAudiosPageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SavedAudiosPageViewModel = hiltViewModel()
){
    //val messages = viewModel.messages.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val messages = viewModel.messages

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                /*is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }*/
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(elevation = 3.dp) {
                Row() {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            viewModel.onEvent(SavedAudiosPageEvent.OnReturnClick)
                        })

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = "Your audios", style = MaterialTheme.typography.h5)
                }
            }


        }
    ){




       padding -> LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(messages) { message ->
                SavedAudioItem(
                    message = message,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        /*OutlinedButton(onClick = { viewModel.onEvent(SavedAudiosPageEvent.SortAudios(SortType.TEXT)) }) {
            Text(text = "Sort By Text")
        }*/
    }


}