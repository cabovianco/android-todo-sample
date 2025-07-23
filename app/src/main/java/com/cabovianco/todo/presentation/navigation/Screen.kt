package com.cabovianco.todo.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object MainScreen : Screen("route")
    data object AddTaskScreen : Screen("add_task")
    data object EditTaskScreen : Screen("edit_task/{id}") {
        fun createRoute(id: Int) = "edit_task/$id"
    }
}
