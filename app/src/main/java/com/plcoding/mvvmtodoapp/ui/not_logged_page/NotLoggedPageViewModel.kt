package com.plcoding.mvvmtodoapp.ui.not_logged_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.Messages
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageEvent
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotLoggedPageViewModel @Inject constructor(

) : ViewModel() {
    var text by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NotLoggedPageEvent) {
        when (event) {

            is NotLoggedPageEvent.onAudioClick -> {
                sendUiEvent(UiEvent.Voice)
            }

            is NotLoggedPageEvent.OnMainMenuClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.START_PAGE))
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