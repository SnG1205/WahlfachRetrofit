package com.plcoding.mvvmtodoapp.ui.guest_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.LoginRepository
import com.plcoding.mvvmtodoapp.data.MessageRepository
import com.plcoding.mvvmtodoapp.data.Messages
import com.plcoding.mvvmtodoapp.util.Routes

import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestPageViewModel @Inject constructor(
    private val repository: MessageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    var text by mutableStateOf("")
    var invisible by mutableStateOf("")
    var time by mutableStateOf<Long>(0)


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
                    repository.insertMessage(
                        Messages(
                            text = text,
                            user_id = user_id.toInt()
                        )
                    )

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
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}