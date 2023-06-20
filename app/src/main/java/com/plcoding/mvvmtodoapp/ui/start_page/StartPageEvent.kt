package com.plcoding.mvvmtodoapp.ui.start_page

import com.plcoding.mvvmtodoapp.ui.add_edit_todo.AddEditTodoEvent

sealed class StartPageEvent{
    object OnLoginClick: StartPageEvent()
    object OnRegisterClick: StartPageEvent()
    object OnGuestClick: StartPageEvent()
    object OnGetDataClick: StartPageEvent()
}
