package ua.opu.lab4shydl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.opu.lab4shydl.data.TaskItem
import ua.opu.lab4shydl.data.local.TaskDao
import ua.opu.lab4shydl.data.mapper.toDomain
import ua.opu.lab4shydl.data.mapper.toEntity

class TaskRepository(private val taskDao: TaskDao) {

    // Отримуємо потік даних, автоматично трансформуючи Entity у доменну модель для UI
    val tasks: Flow<List<TaskItem>> = taskDao.getAllTasks().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getTaskById(id: Int): Flow<TaskItem?> {
        return taskDao.getTaskById(id).map { it?.toDomain() }
    }

    suspend fun addTask(task: TaskItem) {
        taskDao.insertTask(task.toEntity().copy(id = 0)) // База сама згенерує ID
    }

    suspend fun updateTask(task: TaskItem) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun deleteTask(task: TaskItem) {
        taskDao.deleteTask(task.toEntity())
    }
}
