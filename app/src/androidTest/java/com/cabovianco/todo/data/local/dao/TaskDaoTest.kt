package com.cabovianco.todo.data.local.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabovianco.todo.data.local.AppDatabase
import com.cabovianco.todo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            androidx.test.core.app.ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        taskDao = database.taskDao()
    }

    @After
    fun finish() {
        database.close()
    }

    @Test
    fun insert_savesTaskCorrectly() = runTest {
        val task = TaskEntity(id = 1, name = "New task", done = false)
        taskDao.insert(task)

        val result = taskDao.getById(1)
            .first()

        assertEquals(task, result)
    }

    @Test
    fun update_updatesTaskCorrectly() = runTest {
        val task = TaskEntity(id = 1, name = "New task", done = false)
        taskDao.insert(task)

        val updatedTask = task.copy(name = "Updated task")
        taskDao.update(updatedTask)

        val result = taskDao.getById(1)
            .first()

        assertEquals(updatedTask, result)
    }

    @Test
    fun getById_returnsInsertedTask() = runTest {
        val task = TaskEntity(id = 1, name = "New task", done = false)
        taskDao.insert(task)

        val result = taskDao.getById(1)
            .first()

        assertEquals(task, result)
    }

    @Test
    fun delete_deletesTaskCorrectly() = runTest {
        val task = TaskEntity(id = 1, name = "New task", done = false)
        taskDao.insert(task)
        taskDao.delete(task)

        val result = taskDao.getAll()
            .first()

        assertTrue(result.isEmpty())
    }
}
