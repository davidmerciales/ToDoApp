package com.example.mvvmtodo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmtodo.presenter.theme.MVVMToDoTheme
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.AddEditScreen
import com.example.mvvmtodo.presenter.ui.screen.completedTodo.CompletedToDoScreen
import com.example.mvvmtodo.presenter.ui.screen.todo_list.TodoListScreen
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
                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST
                ) {

                    composable(Routes.TODO_LIST) {
                        TodoListScreen(
                            appController = appController
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
                        AddEditScreen(onPopBackStack = {
                            navController.popBackStack()
                        }, appController = appController)
                    }
                    composable(Routes.COMPLETED_TODO) {
                        CompletedToDoScreen(appController = appController)
                    }
                }
            }
        }
    }
}