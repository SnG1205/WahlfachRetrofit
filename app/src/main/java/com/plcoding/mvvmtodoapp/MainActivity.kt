package com.plcoding.mvvmtodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.mvvmtodoapp.ui.add_edit_todo.AddEditTodoScreen
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageScreen
import com.plcoding.mvvmtodoapp.ui.login_page.LoginPageScreen
import com.plcoding.mvvmtodoapp.ui.not_logged_page.NotLoggedPageScreen
import com.plcoding.mvvmtodoapp.ui.register_page.RegisterPageScreen
import com.plcoding.mvvmtodoapp.ui.saved_audios_page.SavedAudiosPageScreen
import com.plcoding.mvvmtodoapp.ui.start_page.StartPageScreen
import com.plcoding.mvvmtodoapp.ui.theme.MVVMTodoAppTheme
import com.plcoding.mvvmtodoapp.ui.todo_list.TodoListScreen
import com.plcoding.mvvmtodoapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTodoAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.START_PAGE
                ) {
                    composable(Routes.START_PAGE) {
                        StartPageScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
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
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                    composable(
                        route = Routes.REGISTER_PAGE
                    ){
                        RegisterPageScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(
                        route = Routes.LOGIN_PAGE
                    ){
                        LoginPageScreen(
                            onNavigate = {
                                navController.navigate((it.route))
                            }
                        )
                    }
                    composable(
                        route = Routes.GUEST_PAGE + "?db_id={db_id}" +"?db_username={db_username}",
                        arguments = listOf(
                            navArgument(name = "db_id"){
                                type = NavType.StringType
                                defaultValue = ""
                            },
                            navArgument(name = "db_username"){
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ){
                        GuestPageScreen(
                            onNavigate = {
                                navController.navigate((it.route))
                            }
                        )
                    }

                    composable(
                        route = Routes.SAVED_AUDIOS_PAGE + "?user_id={user_id}",
                        arguments = listOf(
                            navArgument(name = "user_id"){
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ){
                        SavedAudiosPageScreen(
                            onNavigate = {
                                navController.navigate((it.route))
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(
                        route = Routes.NOT_LOGGED_PAGE
                    ){
                        NotLoggedPageScreen(
                            onNavigate = {
                            navController.navigate(it.route)
                        })
                    }

                }
            }
        }
    }
}