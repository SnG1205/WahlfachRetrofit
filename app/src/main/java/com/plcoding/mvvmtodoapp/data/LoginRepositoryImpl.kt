package com.plcoding.mvvmtodoapp.data

class LoginRepositoryImpl(
    private val dao: TodoDao
) : LoginRepository{

    override suspend fun insertLogin(login: Login) {
        dao.insertLogin(login)
    }

    override suspend fun checkLogin(username: String, password: String): String? {
        return dao.checkLogin(username, password)
    }

    override suspend fun getId(username: String, password: String): Int?{
        return dao.getId(username, password)
    }

}