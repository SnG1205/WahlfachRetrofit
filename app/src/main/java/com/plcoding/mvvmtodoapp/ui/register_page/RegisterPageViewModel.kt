package com.plcoding.mvvmtodoapp.ui.register_page

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.ui.start_page.PostUser
import com.plcoding.mvvmtodoapp.ui.start_page.TestApi
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPageViewModel @Inject constructor(
) : ViewModel() {
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegisterPageEvent) {
        when (event) {
            is RegisterPageEvent.OnUsernameChange -> {
                username = event.username
            }

            is RegisterPageEvent.OnPasswordChange -> {
                password = event.password
            }

            is RegisterPageEvent.OnCreateClick -> {
                viewModelScope.launch {
                    /*if(username.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The username can't be empty"
                        ))
                        return@launch
                    }*/
                    /*repository.insertLogin(
                       Login(
                           username = username,
                           password = password
                       )
                   )*/
                    if (username.replace(" ", "") == "" || password == "") {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Username and password can`t be empty"
                            )
                        )
                        username = ""
                        password = ""
                    } else {
                        if(username.contains(' ') || password.contains(' ')){
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = "No spaces allowed"
                                )
                            )
                            username = ""
                            password = ""
                        }
                        else{
                            TestApi.retrofitService.createUser(PostUser(username, password))
                            sendUiEvent(UiEvent.PopBackStack)
                        }
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