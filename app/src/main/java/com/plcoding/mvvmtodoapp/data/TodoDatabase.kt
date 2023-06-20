package com.plcoding.mvvmtodoapp.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Todo::class, Login::class, Messages::class],
    version = 6,
    autoMigrations = [
        AutoMigration (from = 5, to = 6)
    ],
    exportSchema = true
)
abstract class TodoDatabase: RoomDatabase() {

    abstract val dao: TodoDao
}