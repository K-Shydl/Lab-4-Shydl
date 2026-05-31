package ua.opu.lab4shydl.data

import androidx.compose.ui.graphics.Color

enum class TaskPriority(val title: String, val color: Color) {
    LOW("НИЗЬКИЙ", Color(0xFF4CAF50)),
    MEDIUM("СЕРЕДНІЙ", Color(0xFFFF9800)),
    HIGH("ВИСОКИЙ", Color(0xFFF44336))
}