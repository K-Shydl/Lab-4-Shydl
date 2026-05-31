package ua.opu.lab4shydl.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Автоматична генерація цілих чисел для бази даних
    val title: String,
    val description: String,
    val priority: String, // Зберігатимемо як String (наприклад, "HIGH", "MEDIUM", "LOW")
    val isCompleted: Boolean
)
