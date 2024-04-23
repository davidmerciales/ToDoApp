package com.example.mvvmtodo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmtodo.presenter.theme.MVVMToDoTheme
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.CollectMessages
import com.example.mvvmtodo.presenter.ui.navigation.CollectRoutes
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.AddEditViewModel
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.TaskAddEditScreen
import com.example.mvvmtodo.presenter.ui.screen.completedTodo.CompletedToDoViewModel
import com.example.mvvmtodo.presenter.ui.screen.completedTodo.TaskManagementCompletedScreen
import com.example.mvvmtodo.presenter.ui.screen.login.LoginScreen
import com.example.mvvmtodo.presenter.ui.screen.todo_list.TaskManagementScreen
import com.example.mvvmtodo.presenter.ui.screen.todo_list.TodoListViewModel
import com.example.mvvmtodo.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appController: AppController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMToDoTheme {
                val navController = rememberNavController()
                appController.navController = navController
                appController.CollectRoutes()

                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST
                ) {

                    composable(Routes.LOGIN) {
                        LoginScreen()
                    }

                    composable(Routes.TODO_LIST) {
                        val viewModel: TodoListViewModel = hiltViewModel()
                        TaskManagementScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val viewModel: AddEditViewModel = hiltViewModel()
                        TaskAddEditScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                            appController = appController,
                            onPopBackStack = { navController.popBackStack() })
                    }

                    composable(Routes.COMPLETED_TODO) {
                        val viewModel: CompletedToDoViewModel = hiltViewModel()
                        TaskManagementCompletedScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}

val LocalNavigation = compositionLocalOf<String> {
    throw Exception("No local navigation")
}

@Composable
fun NavigationContext(
    snackbarHostState: SnackbarHostState,
    appController: AppController,
    context: @Composable () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    appController.CollectMessages(snackbarHostState = snackbarHostState) { result ->
        if (result == SnackbarResult.ActionPerformed) {
            isChecked = true
        }
    }

    context()
}