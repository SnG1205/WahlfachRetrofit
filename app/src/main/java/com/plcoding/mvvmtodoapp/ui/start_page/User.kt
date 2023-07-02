package com.plcoding.mvvmtodoapp.ui.start_page

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int?, val username: String?
)
