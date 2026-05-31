package ua.opu.lab4shydl.data.mapper

import ua.opu.lab4shydl.data.TaskItem
import ua.opu.lab4shydl.data.TaskPriority
import ua.opu.lab4shydl.data.local.TaskEntity

// Перетворення з бази даних в об'єкт для UI
fun TaskEntity.toDomain(): TaskItem {
    val domainPriority = try {
        TaskPriority.valueOf(this.priority)
    } catch (e: Exception) {
        TaskPriority.MEDIUM
    }
    return TaskItem(
        id = this.id.toString(), // Конвертуємо Int бази даних у String для вашої навігації
        title = this.title,
        description = this.description,
        priority = domainPriority,
        isCompleted = this.isCompleted
    )
}

// Перетворення з UI об'єкта в сутність бази даних
fun TaskItem.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id.toIntOrNull() ?: 0,
        title = this.title,
        description = this.description,
        priority = this.priority.name, // Зберігаємо ім'я Enum константи
        isCompleted = this.isCompleted
    )
}
