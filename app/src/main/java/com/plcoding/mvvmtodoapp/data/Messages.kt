package com.plcoding.mvvmtodoapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Login::class,
        childColumns = ["user_id"],
        parentColumns = ["id"]
    )])
data class Messages(
    val text : String,
    val user_id : Int? = null,
    @PrimaryKey(autoGenerate = true)  val id: Int? = null
)
