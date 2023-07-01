package com.plcoding.mvvmtodoapp.ui.guest_page

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.MainActivity
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GuestPageScreen(
    viewModel: GuestPageViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
    ){

    var canRecord by remember {
        mutableStateOf(false)
    }


    val voiceState by viewModel.voiceToText.state.collectAsState()

    val context = LocalContext.current

    // Creates an permission request
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            canRecord = isGranted
        }
    )

    var showMenu by remember {mutableStateOf(false)}

    LaunchedEffect(key1 = true) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
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

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedButton(onClick = {
                /*if (!voiceState.isSpeaking) {
                    viewModel.voiceToText.startListening("en")
                } else {
                    viewModel.voiceToText.stopListening()
                    viewModel.randomText = voiceState.spokenText
                }*/
            }) {
                Text(text = "Record")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.invisible)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.user_id)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.time.toString())
            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = viewModel.randomText)
            Spacer(modifier = Modifier.padding(8.dp))

            AnimatedContent(targetState = voiceState.isSpeaking) { isSpeaking ->
                if (isSpeaking) {
                    Text(
                        text = "Speak...",
                        )
                } else {
                    Text(
                        text = voiceState.spokenText.ifEmpty { "Click on record" },
                    )
                }
            }
        }
    }
}



