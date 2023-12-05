package com.example.mvvmtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmtodo.ui.screen.addEditTodo.AddEditScreen
import com.example.mvvmtodo.ui.screen.todo_list.TodoListScreen
import com.example.mvvmtodo.ui.theme.MVVMToDoTheme
import com.example.mvvmtodo.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMToDoTheme {
                val navController =  rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST){

                    composable(Routes.TODO_LIST){
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            })
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        AddEditScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}