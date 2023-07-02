package com.plcoding.mvvmtodoapp.ui.guest_page

import android.app.Application
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.LoginRepository
import com.plcoding.mvvmtodoapp.data.MessageRepository
import com.plcoding.mvvmtodoapp.data.Messages
import com.plcoding.mvvmtodoapp.ui.start_page.PostMessages
import com.plcoding.mvvmtodoapp.ui.start_page.TestApi
import com.plcoding.mvvmtodoapp.util.Routes


import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GuestPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    application: Application
): ViewModel(){

    var text by mutableStateOf("")
    var invisible by mutableStateOf("")
    var time by mutableStateOf<Long>(0)
    var randomText by mutableStateOf("Empty now")
    var recorded by mutableStateOf("")


    val user_id = savedStateHandle.get<String>("db_id")!!
    val user = savedStateHandle.get<String>("db_username")!!

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: GuestPageEvent) {
        when(event) {

            is GuestPageEvent.OnTextChange -> {
                text = event.text
            }

            is GuestPageEvent.OnSaveClick -> {
                viewModelScope.launch {

                    invisible = "You can see me"
                    time = System.currentTimeMillis()
                    /*if(username.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The username can't be empty"
                        ))
                        return@launch
                    }*/
                    /*repository.insertMessage(
                        Messages(
                            text = text,
                            user_id = user_id.toInt()
                        )
                    )*/
                    TestApi.retrofitService.createMessage(PostMessages(event.text, user_id.toInt()))
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Message successfully created"
                    ))
                }
            }

            is GuestPageEvent.OnLogOutClick ->{
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.START_PAGE))
                }
            }

            is GuestPageEvent.OnSavedAudiosClick ->{
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.SAVED_AUDIOS_PAGE + "?user_id=${user_id}"))
                }
            }

            is GuestPageEvent.OnRecordClick ->{
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Voice)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
