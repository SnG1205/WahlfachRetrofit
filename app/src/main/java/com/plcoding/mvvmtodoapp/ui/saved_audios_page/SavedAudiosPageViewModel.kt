package com.plcoding.mvvmtodoapp.ui.saved_audios_page

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.MessageRepository
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedAudiosPageViewModel @Inject constructor(
    private val repository: MessageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val user_id = savedStateHandle.get<String>("user_id")!!

    val sortType = MutableStateFlow(SortType.NONE)

    //val messages = repository.getMessages(user_id.toInt())

    val messages = sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.NONE -> repository.getMessages(user_id.toInt())
                SortType.TEXT -> repository.getMessagesByTextAsc(user_id.toInt())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SavedAudiosPageEvent) {
        when(event) {

            is SavedAudiosPageEvent.OnReturnClick -> {
                //sendUiEvent(UiEvent.Navigate(Routes.GUEST_PAGE))
                sendUiEvent((UiEvent.PopBackStack))
            }

            is SavedAudiosPageEvent.OnDeleteClick -> {
                viewModelScope.launch {

                    /*if(username.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The username can't be empty"
                        ))
                        return@launch
                    }*/
                    /*repository.deleteMessage(
                        event.message
                    )*/
                    repository.deleteByText(event.message.text)

                }
            }
            is SavedAudiosPageEvent.SortAudios ->{
                sortType.value = event.sortType
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}