package com.plcoding.mvvmtodoapp.ui.login_page

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.Login
import com.plcoding.mvvmtodoapp.data.LoginRepository
import com.plcoding.mvvmtodoapp.ui.register_page.RegisterPageEvent
import com.plcoding.mvvmtodoapp.ui.start_page.TestApi
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel(){
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var db_username by mutableStateOf("")

    var db_id by mutableStateOf("")

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginPageEvent) {
        when(event) {
            is LoginPageEvent.OnUsernameChange -> {
                username = event.username
            }
            is LoginPageEvent.OnPasswordChange -> {
                password = event.password
            }
            is LoginPageEvent.OnLoginClick -> {
                viewModelScope.launch {
                    /*if(username.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The username can't be empty"
                        ))
                        return@launch
                    }*/
                    /*db_username = repository.checkLogin(
                        username = username,
                        password = password
                    ).toString()*/
                    db_username = TestApi.retrofitService.getUsername(username, password)[0].username

                    /*db_id = repository.getId(
                        username, password
                    ).toString()*/
                    db_id =TestApi.retrofitService.getUsername(username, password)[0].id.toString()

                    if(db_username == username) {
                        sendUiEvent(UiEvent.Navigate(Routes.GUEST_PAGE + "?db_id=${db_id}" +"?db_username=${db_username}"))
                    }
                    else{
                        db_username = "Not working"
                    }
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