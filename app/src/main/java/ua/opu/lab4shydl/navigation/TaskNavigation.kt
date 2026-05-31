package ua.opu.lab4shydl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.opu.lab4shydl.data.local.DatabaseProvider
import ua.opu.lab4shydl.data.repository.TaskRepository
import ua.opu.lab4shydl.ui.screens.AddEditTaskScreen
import ua.opu.lab4shydl.ui.screens.DetailsTaskScreen
import ua.opu.lab4shydl.ui.screens.TasksListScreen
import ua.opu.lab4shydl.ui.viewmodel.TasksViewModel
import ua.opu.lab4shydl.ui.viewmodel.TasksViewModelFactory

object TaskRoutes {
    const val LIST = "tasks_list"
    const val ADD_EDIT = "add_edit_task?taskId={taskId}"
    const val DETAILS = "task_details/{taskId}"

    fun navigateToAdd() = "add_edit_task?taskId="
    fun navigateToEdit(id: String) = "add_edit_task?taskId=$id"
    fun navigateToDetails(id: String) = "task_details/$id"
}

@Composable
fun TaskNavigation() {
    val navController = rememberNavController()
    // НАЛАШТУВАННЯ ROOM ТА РЕПОЗИТОРІЮ НА РІВНІ НАВІГАЦІЇ
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = TaskRepository(database.taskDao())

    // Створення ViewModel через Фабрику
    val sharedViewModel: TasksViewModel = viewModel(
        factory = TasksViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = TaskRoutes.LIST) {
        composable(TaskRoutes.LIST) {
            TasksListScreen(
                viewModel = sharedViewModel,
                onAddTaskClick = { navController.navigate(TaskRoutes.navigateToAdd()) },
                onTaskClick = { id -> navController.navigate(TaskRoutes.navigateToDetails(id)) }
            )
        }

        composable(
            route = TaskRoutes.ADD_EDIT,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType; nullable = true; defaultValue = null })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            AddEditTaskScreen(
                taskId = taskId,
                viewModel = sharedViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = TaskRoutes.DETAILS,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            DetailsTaskScreen(
                taskId = taskId,
                viewModel = sharedViewModel,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { id -> navController.navigate(TaskRoutes.navigateToEdit(id)) },
                onDeleteClick = { navController.popBackStack(TaskRoutes.LIST, false) }
            )
        }
    }
}