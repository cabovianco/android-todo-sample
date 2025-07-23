package com.cabovianco.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cabovianco.todo.data.local.dao.TaskDao
import com.cabovianco.todo.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
