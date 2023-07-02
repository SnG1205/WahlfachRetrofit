package com.plcoding.mvvmtodoapp.ui.guest_page

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.MainActivity
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GuestPageScreen(
    viewModel: GuestPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onVoice: () -> Unit,
    recordedMessage: String
) {

    val scaffoldState = rememberScaffoldState()
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }

                is UiEvent.Voice -> onVoice()
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "Welcome, ${viewModel.user}!") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = { viewModel.onEvent(GuestPageEvent.OnSavedAudiosClick) }) {
                            Text(text = "Saved audios")
                        }
                        DropdownMenuItem(onClick = {
                            viewModel.onEvent(GuestPageEvent.OnLogOutClick)
                        }) {
                            Text(text = "Log out")
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
            Text(text = "Welcome to  Guest Page.")
            Text(text = "Click on button below to start recording")
            //, click on button below to start recording

            //Spacer(modifier = Modifier.padding(8.dp))

            /*TextField(
                value = viewModel.text ,
                onValueChange = {
                    viewModel.onEvent(GuestPageEvent.OnTextChange(it))
                },
                placeholder = {
                    Text(text = "Note")
                },
                modifier = Modifier.fillMaxWidth()
            )*/

            Spacer(modifier = Modifier.padding(16.dp))

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
                    viewModel.onEvent(GuestPageEvent.OnRecordClick)
                },
                Modifier.size(120.dp, 40.dp)
            ) {
                Text(text = "Record")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = {
                    viewModel.onEvent(GuestPageEvent.OnSaveClick(recordedMessage))
                    viewModel.text = ""
                },
                Modifier.size(120.dp, 40.dp)
            ) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = recordedMessage)
        }
    }
}




