package com.plcoding.mvvmtodoapp.ui.saved_audios_page

import com.plcoding.mvvmtodoapp.ui.start_page.Messages

sealed class SavedAudiosPageEvent{
    data class OnDeleteClick(val message: Messages) : SavedAudiosPageEvent()
    object OnReturnClick: SavedAudiosPageEvent()
    data class SortAudios(val sortType: SortType): SavedAudiosPageEvent()
}
