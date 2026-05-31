package ua.opu.lab4shydl.data

import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val isCompleted: Boolean = false
)
