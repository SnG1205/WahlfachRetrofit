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
    var display = mutableListOf<User>()
    var logins = mutableListOf<Logins>()
    var messages = mutableListOf<Messages>()



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
                sendUiEvent(UiEvent.Navigate(Routes.NOT_LOGGED_PAGE))
            }
            is StartPageEvent.OnGetDataClick ->{
                viewModelScope.launch {
                    //json = TestApi.retrofitService.getUsername("SnG1205", "SnG1205")[0].id.toString()
                    //logins = TestApi.retrofitService.getLogins().toMutableList()
                    //display = listResult as MutableList<Logins>
                    //display = TestApi.retrofitService.getUsername("SnG1205", "SnG1205").toMutableList()
                    //messages = TestApi.retrofitService.getMessagesById(1).toMutableList()
                    //TestApi.retrofitService.createUser(PostUser("Retrofit", "Retrofit"))
                    //TestApi.retrofitService.createMessage(PostMessages("Retrofit message", 1))
                    TestApi.retrofitService.deleteMessage("Retrofit message")
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