package com.plcoding.mvvmtodoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(login: Login)

    @Query("SELECT username FROM login WHERE username = :username and password = :password")
    suspend fun  checkLogin(username: String, password: String): String?

    @Query("SELECT id FROM login WHERE username = :username and password = :password")
    suspend fun getId(username: String, password: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Messages)

    @Delete(entity = Messages::class)
    suspend fun deleteMessage(message: Messages)

    @Query("SELECT text FROM messages where user_id = :user_id")
    fun getMessages(user_id: Int) : Flow<List<Messages>>

    @Query("DELETE FROM messages WHERE text = :text")
    suspend fun deleteByText(text: String)

    @Query("SELECT text FROM messages where user_id = :user_id ORDER BY TEXT ASC")
    fun getMessagesByTextAsc(user_id: Int): Flow<List<Messages>>
}