package com.plcoding.mvvmtodoapp.ui.start_page

import kotlinx.serialization.Serializable

@Serializable
data class PostMessages(
    val mess: String, val user_id: Int
)
