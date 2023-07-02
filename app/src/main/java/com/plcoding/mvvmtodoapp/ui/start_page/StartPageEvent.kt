package com.plcoding.mvvmtodoapp.ui.start_page


sealed class StartPageEvent{
    object OnLoginClick: StartPageEvent()
    object OnRegisterClick: StartPageEvent()
    object OnGuestClick: StartPageEvent()
    object OnGetDataClick: StartPageEvent()
}
