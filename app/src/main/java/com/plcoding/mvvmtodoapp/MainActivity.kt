package com.plcoding.mvvmtodoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.mvvmtodoapp.ui.guest_page.GuestPageScreen
import com.plcoding.mvvmtodoapp.ui.login_page.LoginPageScreen
import com.plcoding.mvvmtodoapp.ui.not_logged_page.NotLoggedPageScreen
import com.plcoding.mvvmtodoapp.ui.register_page.RegisterPageScreen
import com.plcoding.mvvmtodoapp.ui.saved_audios_page.SavedAudiosPageScreen
import com.plcoding.mvvmtodoapp.ui.start_page.StartPageScreen
import com.plcoding.mvvmtodoapp.ui.theme.MVVMTodoAppTheme
import com.plcoding.mvvmtodoapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var outputTxt by mutableStateOf("")
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
                        route = Routes.REGISTER_PAGE
                    ) {
                        RegisterPageScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(
                        route = Routes.LOGIN_PAGE
                    ) {
                        LoginPageScreen(
                            onNavigate = {
                                navController.navigate((it.route))
                            }
                        )
                    }
                    composable(
                        route = Routes.GUEST_PAGE + "?db_id={db_id}" + "?db_username={db_username}",
                        arguments = listOf(
                            navArgument(name = "db_id") {
                                type = NavType.StringType
                                defaultValue = ""
                            },
                            navArgument(name = "db_username") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ) {
                        GuestPageScreen(
                            onNavigate = {
                                navController.navigate((it.route))
                            },
                            onVoice = {
                                getSpeechInput()
                            },
                            recordedMessage = outputTxt
                        )
                    }

                    composable(
                        route = Routes.SAVED_AUDIOS_PAGE + "?user_id={user_id}",
                        arguments = listOf(
                            navArgument(name = "user_id") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ) {
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
                    ) {
                        NotLoggedPageScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            onVoice = {
                                getSpeechInput()
                            },
                            recordedMessage = outputTxt
                        )
                    }

                }
            }
        }
    }

    fun getSpeechInput() {

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")

            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            outputTxt = result?.get(0).toString()
        }
    }
}