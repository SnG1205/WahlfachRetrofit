package com.plcoding.mvvmtodoapp.ui.saved_audios_page

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.mvvmtodoapp.data.MessageRepository
import com.plcoding.mvvmtodoapp.ui.start_page.Messages
import com.plcoding.mvvmtodoapp.ui.start_page.TestApi
import com.plcoding.mvvmtodoapp.util.Routes
import com.plcoding.mvvmtodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    var messages = mutableListOf<Messages>()

    //val messages = repository.getMessages(user_id.toInt())

    /*@OptIn(ExperimentalCoroutinesApi::class)
    val messages = sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.NONE ->   flow<List<Messages>> { TestApi.retrofitService.getMessagesById(user_id.toInt()).toMutableList() }//repository.getMessages(user_id.toInt())
                SortType.TEXT -> flow { TestApi.retrofitService.getMessagesById(user_id.toInt()).toMutableList() } //repository.getMessagesByTextAsc(user_id.toInt())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val messages = TestApi.retrofitService.getMessagesById(1).toMutableList()*/

    init {
        viewModelScope.launch {
            messages = TestApi.retrofitService.getMessagesById(user_id.toInt()).toMutableList()
        }

    }


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
                    //repository.deleteByText(event.message.text)
                    TestApi.retrofitService.deleteMessage(event.message.text)

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