package com.plcoding.mvvmtodoapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
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
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var outputTxt by mutableStateOf("Empty")
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

     fun getSpeechInput(context: Context) {
        // on below line we are checking if speech
        // recognizer intent is present or not.
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            // if the intent is not present we are simply displaying a toast message.
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            // on below line we are calling a speech recognizer intent
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on the below line we are specifying language model as language web search
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )

            // on below line we are specifying extra language as default english language
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            // on below line we are specifying prompt as Speak something
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")

            // at last we are calling start activity
            // for result to start our activity.
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if the request
        // code is same and result code is ok
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            // if the condition is satisfied we are getting
            // the data from our string array list in our result.
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            // on below line we are setting result
            // in our output text method.
            outputTxt = result?.get(0).toString()
        }
    }
}