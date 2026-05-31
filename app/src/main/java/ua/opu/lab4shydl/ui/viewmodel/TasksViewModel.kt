package ua.opu.lab4shydl.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.opu.lab4shydl.data.TaskItem
import ua.opu.lab4shydl.data.TaskPriority
import ua.opu.lab4shydl.data.repository.TaskRepository

class TasksViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks = repository.tasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getTaskById(id: String?): Flow<TaskItem?> {
        val intId = id?.toIntOrNull() ?: return kotlinx.coroutines.flow.flowOf(null)
        return repository.getTaskById(intId)
    }

    fun addOrUpdateTask(id: String?, title: String, description: String, priority: TaskPriority) {
        viewModelScope.launch {
            if (id.isNullOrEmpty()) {
                val newTask = TaskItem(
                    id = "0",
                    title = title,
                    description = description,
                    priority = priority,
                    isCompleted = false
                )
                repository.addTask(newTask)
            } else {
                val currentTask = getTaskById(id).firstOrNull()
                if (currentTask != null) {
                    val updated = currentTask.copy(
                        title = title,
                        description = description,
                        priority = priority
                    )
                    repository.updateTask(updated)
                }
            }
        }
    }

    fun toggleTaskCompletion(id: String) {
        viewModelScope.launch {
            val currentTask = getTaskById(id).firstOrNull()
            if (currentTask != null) {
                val updated = currentTask.copy(isCompleted = !currentTask.isCompleted)
                repository.updateTask(updated)
            }
        }
    }

    fun deleteTask(task: TaskItem) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}

class TasksViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}