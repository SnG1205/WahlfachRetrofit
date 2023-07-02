package com.plcoding.mvvmtodoapp.ui.guest_page

import com.plcoding.mvvmtodoapp.ui.add_edit_todo.AddEditTodoEvent

sealed class GuestPageEvent{
    data class OnTextChange(val text: String):GuestPageEvent()
    object OnSaveClick: GuestPageEvent()
    object OnLogOutClick: GuestPageEvent()
    object OnSavedAudiosClick: GuestPageEvent()
    object OnRecordClick: GuestPageEvent()
}
