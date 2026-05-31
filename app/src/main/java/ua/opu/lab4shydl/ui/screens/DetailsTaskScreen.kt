package ua.opu.lab4shydl.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.opu.lab4shydl.ui.viewmodel.TasksViewModel

@Composable
fun DetailsTaskScreen(
    taskId: String,
    viewModel: TasksViewModel,
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    // ВАЖЛИВО: Спостерігаємо за всім списком завдань через State,
    // щоб екран реагував на будь-які точкові зміни прапорців всередині об'єктів
    val tasks by viewModel.tasks.collectAsState()
    val task = tasks.find { it.id == taskId }

    if (task == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Завдання не знайдено")
        }
        return
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
                        text = "Деталі завдання",
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = "До завтра", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = task.priority.color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = task.priority.title,
                            color = task.priority.color,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Опис", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = task.description, fontSize = 16.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { viewModel.toggleTaskCompletion(taskId) }
                )
                Text("Позначити як виконане", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onEditClick(taskId) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) { Text("Редагувати", fontSize = 16.sp) }

            OutlinedButton(
                onClick = {
                    viewModel.deleteTask(task)
                    onDeleteClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Видалити", fontSize = 16.sp, color = Color.Red) }

            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Назад", color = Color.Gray)
                }
            }
        }
    }
}