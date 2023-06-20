package com.plcoding.mvvmtodoapp.ui.guest_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun GuestPageScreen(
    viewModel: GuestPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
    ){

    var showMenu by remember {mutableStateOf(false)}

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Welcome, ${viewModel.user}!")},
                actions = {
                    IconButton(onClick = {showMenu = !showMenu}) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = { viewModel.onEvent(GuestPageEvent.OnSavedAudiosClick) }) {
                            Text(text = "Saved audios")
                        }
                        DropdownMenuItem(onClick = { viewModel.onEvent(GuestPageEvent.OnLogOutClick) }) {
                            Text(text = "Log out")
                        }
                    }
                }
            )


        }
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
            Arrangement.Center
        ) {
            Text(text = "Welcome to  Guest Page, Development is in progress")

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.text ,
                onValueChange = {
                    viewModel.onEvent(GuestPageEvent.OnTextChange(it))
                },
                placeholder = {
                    Text(text = "Note")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedButton(onClick = {
                viewModel.onEvent(GuestPageEvent.OnSaveClick)
                viewModel.text =""
            }) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.invisible)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.user_id)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.time.toString())
        }
    }

}