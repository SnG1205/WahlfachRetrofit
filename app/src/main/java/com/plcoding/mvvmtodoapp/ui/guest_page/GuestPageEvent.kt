package com.plcoding.mvvmtodoapp.ui.guest_page


sealed class GuestPageEvent{
    data class OnTextChange(val text: String):GuestPageEvent()
    data class OnSaveClick(val text: String): GuestPageEvent()
    object OnLogOutClick: GuestPageEvent()
    object OnSavedAudiosClick: GuestPageEvent()
    object OnRecordClick: GuestPageEvent()
}
