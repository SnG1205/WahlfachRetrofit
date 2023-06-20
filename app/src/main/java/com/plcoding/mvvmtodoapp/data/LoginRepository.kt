package com.plcoding.mvvmtodoapp.data

import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun insertLogin(login: Login)

    suspend fun  checkLogin(username: String, password: String): String?

    suspend fun getId(username: String, password: String): Int?
}