package ua.opu.lab4shydl.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.opu.lab4shydl.data.TaskPriority
import ua.opu.lab4shydl.ui.viewmodel.TasksViewModel

@Composable
fun AddEditTaskScreen(
    taskId: String?,
    viewModel: TasksViewModel,
    onNavigateBack: () -> Unit
) {
    // Асинхронно отримуємо завдання з БД
    val existingTask by viewModel.getTaskById(taskId).collectAsState(initial = null)

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var expanded by remember { mutableStateOf(false) }

    // ВИПРАВЛЕННЯ: Як тільки existingTask завантажиться з БД, ми перезаписуємо поля форми
    LaunchedEffect(existingTask) {
        existingTask?.let { task ->
            title = task.title
            description = task.description
            priority = task.priority
        }
    }

    Scaffold(
        topBar = {
            Surface(color = Color(0xFF2196F3)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (taskId == null) "Нове завдання" else "Редагування",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Назва завдання") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Опис") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Text("Пріоритет", fontWeight = FontWeight.SemiBold, color = Color.Gray)
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .clickable { expanded = true }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(priority.title, color = priority.color, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    TaskPriority.values().forEach { priorityOption ->
                        DropdownMenuItem(
                            text = { Text(priorityOption.title, color = priorityOption.color, fontWeight = FontWeight.Bold) },
                            onClick = {
                                priority = priorityOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.addOrUpdateTask(taskId, title, description, priority)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) { Text("Створити / Зберегти", fontSize = 16.sp) }

            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Скасувати", fontSize = 16.sp) }
        }
    }
}