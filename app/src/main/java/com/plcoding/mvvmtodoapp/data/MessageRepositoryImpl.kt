package com.plcoding.mvvmtodoapp.data

import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl(
    private val dao: TodoDao
) : MessageRepository{

    override suspend fun insertMessage(message: Messages) {
        dao.insertMessage(message)
    }

    override suspend fun deleteMessage(message: Messages) {
        dao.deleteMessage(message)
    }

    override  fun getMessages(user_id: Int): Flow<List<Messages>> {
        return dao.getMessages(user_id)
    }

    override suspend fun deleteByText(text: String){
        dao.deleteByText(text)
    }

    override fun getMessagesByTextAsc(user_id: Int): Flow<List<Messages>>{
        return dao.getMessagesByTextAsc(user_id)
    }
}