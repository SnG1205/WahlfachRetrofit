package com.plcoding.mvvmtodoapp.ui.not_logged_page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageEvent
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageViewModel
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotLoggedPageScreen(
    viewModel: NotLoggedPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onVoice: () -> Unit,
    recordedMessage: String
) {

    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.Voice -> onVoice()
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Welcome, anonymous user") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Text(text = "Welcome to  Guest Page, Development is in progress")

            Spacer(modifier = Modifier.padding(8.dp))


            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedButton(
                onClick = {
                    /*if (!voiceState.isSpeaking) {
                        viewModel.voiceToText.startListening("en")
                    } else {
                        viewModel.voiceToText.stopListening()
                        viewModel.randomText = voiceState.spokenText
                    }*/
                    //voiceGoogle.getSpeechInput()
                    //viewModel.onEvent(GuestPageEvent.OnRecordClick)
                    //voiceGoogle.getSpeechInput(context)
                    //main.getSpeechInput()
                    viewModel.onEvent(NotLoggedPageEvent.onAudioClick)
                },
                Modifier.size(120.dp, 40.dp)
            ) {
                Text(text = "Record")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = recordedMessage)


        }
    }

}