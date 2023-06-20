package com.plcoding.mvvmtodoapp.ui.start_page

import kotlinx.serialization.Serializable
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import kotlinx.serialization.json.Json
import okhttp3.MediaType


@Serializable
data class Logins(
    val id: String, val username: String, val password: String
)
