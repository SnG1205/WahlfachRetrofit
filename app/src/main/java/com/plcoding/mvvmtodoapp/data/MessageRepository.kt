package com.plcoding.mvvmtodoapp.data

import kotlinx.coroutines.flow.Flow


interface MessageRepository {

    suspend fun insertMessage(message: Messages)

    suspend fun deleteMessage(message: Messages)

    fun getMessages(user_id: Int) : Flow<List<Messages>>

    fun getMessagesByTextAsc(user_id: Int): Flow<List<Messages>>

    suspend fun deleteByText(text: String)

}