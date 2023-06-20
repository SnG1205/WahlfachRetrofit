package com.plcoding.mvvmtodoapp.ui.register_page

import com.plcoding.mvvmtodoapp.ui.add_edit_todo.AddEditTodoEvent

sealed class RegisterPageEvent{
    data class OnUsernameChange(val username: String): RegisterPageEvent()
    data class OnPasswordChange(val password: String): RegisterPageEvent()
    object OnCreateClick: RegisterPageEvent()
}
