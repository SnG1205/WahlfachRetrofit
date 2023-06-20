package com.plcoding.mvvmtodoapp.ui.start_page

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartPageViewModel @Inject constructor() : ViewModel() {
    var json by  mutableStateOf("")
    //var listResult = listOf<Logins>()
    var display = mutableListOf<Logins>()



    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: StartPageEvent) {
        when(event){
            is StartPageEvent.OnLoginClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.LOGIN_PAGE))
            }
            is StartPageEvent.OnRegisterClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.REGISTER_PAGE))
            }
            is StartPageEvent.OnGuestClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.GUEST_PAGE))
            }
            is StartPageEvent.OnGetDataClick ->{
                viewModelScope.launch {
                    json = TestApi.retrofitService.getLogins()
                    //display = listResult as MutableList<Logins>

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