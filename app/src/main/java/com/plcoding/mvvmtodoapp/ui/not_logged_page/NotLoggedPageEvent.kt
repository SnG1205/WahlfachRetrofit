package com.plcoding.mvvmtodoapp.ui.not_logged_page

import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageEvent

sealed class NotLoggedPageEvent{
    object onAudioClick: NotLoggedPageEvent()
    object OnMainMenuClick: NotLoggedPageEvent()
}
