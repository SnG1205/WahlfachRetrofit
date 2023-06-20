package com.plcoding.mvvmtodoapp.ui.not_logged_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageEvent
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageViewModel
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun NotLoggedPageScreen(
    viewModel: NotLoggedPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
){

    var showMenu by remember { mutableStateOf(false) }

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
            TopAppBar(title = { Text(text = "Welcome, anonymous user") },
                actions = {
                    IconButton(onClick = {showMenu = !showMenu}) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = { viewModel.onEvent(NotLoggedPageEvent.OnMainMenuClick) }) {
                            Text(text = "Start Page")
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
            //Text(text = "Welcome to  Guest Page, Development is in progress")

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.text ,
                onValueChange = {
                    viewModel.onEvent(NotLoggedPageEvent.OnTextChange(it))
                },
                placeholder = {
                    Text(text = "Note")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))



        }
    }

}