package com.cabovianco.todo.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cabovianco.todo.R
import com.cabovianco.todo.presentation.ui.screen.AddTaskScreen
import com.cabovianco.todo.presentation.ui.screen.EditTaskScreen
import com.cabovianco.todo.presentation.ui.screen.MainScreen
import com.cabovianco.todo.presentation.viewmodel.AddTaskViewModel
import com.cabovianco.todo.presentation.viewmodel.EditTaskViewModel
import com.cabovianco.todo.presentation.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(
                    when (currentRoute) {
                        Screen.AddTaskScreen.route -> R.string.add_task_topbar_title
                        Screen.EditTaskScreen.route -> R.string.edit_task_topbar_dialog
                        else -> R.string.app_name
                    }
                ),
                onNavigateUp = { navController.navigateUp() },
                onAddTaskAction = { navController.navigate(Screen.AddTaskScreen.route) },
                canNavigateUp = currentRoute != Screen.MainScreen.route,
                showAddTaskAction = currentRoute == Screen.MainScreen.route
            )
        }
    ) { padding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    onNavigateUp: () -> Unit,
    onAddTaskAction: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateUp: Boolean = false,
    showAddTaskAction: Boolean = false
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            if (canNavigateUp) {
                ActionButton(iconResId = R.drawable.navigate_up, onClick = onNavigateUp)
            }
        },
        actions = {
            if (showAddTaskAction) {
                ActionButton(iconResId = R.drawable.add, onClick = onAddTaskAction)
            }
        }
    )
}

@Composable
private fun ActionButton(iconResId: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            val mainViewModel: MainViewModel = hiltViewModel()
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

            MainScreen(
                uiState = uiState,
                onClickTask = { navController.navigate(Screen.EditTaskScreen.createRoute(it)) },
                onLongClickTask = { task -> mainViewModel.deleteTask(task) },
                onCheckedChange = { task, value -> mainViewModel.onCheckedChange(task, value) },
            )
        }

        composable(
            route = Screen.AddTaskScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            val addTaskViewModel: AddTaskViewModel = hiltViewModel()
            val uiState by addTaskViewModel.uiState.collectAsState()

            AddTaskScreen(
                taskName = uiState.taskName,
                onTaskNameChange = { value -> addTaskViewModel.onTaskNameChange(value) },
                isTaskValid = uiState.isTaskValid,
                onCancel = { navController.navigateUp() },
                onSave = {
                    if (addTaskViewModel.addTask()) {
                        navController.navigateUp()
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }

        composable(
            route = Screen.EditTaskScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("id") ?: -1

            val editTaskViewModel: EditTaskViewModel = hiltViewModel()
            val uiState by editTaskViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { editTaskViewModel.loadTask(taskId) }

            EditTaskScreen(
                taskName = uiState.taskName,
                onTaskNameChange = { value -> editTaskViewModel.onTaskNameChange(value) },
                isTaskValid = uiState.isTaskValid,
                onCancel = { navController.navigateUp() },
                onSave = {
                    if (editTaskViewModel.saveTask()) {
                        navController.navigateUp()
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            )
        }
    }
}
