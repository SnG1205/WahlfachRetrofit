package com.plcoding.mvvmtodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Login(
    val username: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)  val id: Int? = null
)
