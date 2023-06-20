package com.plcoding.mvvmtodoapp.ui.login_page

sealed class LoginPageEvent{
    data class OnUsernameChange(val username: String): LoginPageEvent()
    data class OnPasswordChange(val password: String): LoginPageEvent()
    object OnLoginClick: LoginPageEvent()
}
