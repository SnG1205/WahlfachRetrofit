package com.plcoding.mvvmtodoapp.ui.login_page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.ui.register_page.RegisterPageEvent
import com.plcoding.mvvmtodoapp.ui.register_page.RegisterPageViewModel
import com.plcoding.mvvmtodoapp.ui.todo_list.TodoListEvent
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginPageScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title =
            { Text(text = "Login Page") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to  Login Page. Enter Your data to continue.")

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.username,
                onValueChange = {
                    viewModel.onEvent(LoginPageEvent.OnUsernameChange(it))
                },
                placeholder = {
                    Text(text = "username")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.password,
                onValueChange = {
                    viewModel.onEvent(LoginPageEvent.OnPasswordChange(it))
                },
                placeholder = {
                    Text(text = "password")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedButton(
                onClick = {
                    viewModel.onEvent(LoginPageEvent.OnLoginClick)
                },
                Modifier.size(120.dp, 40.dp)
            ) {
                Text(text = "Log in")
            }

            //Text(text = viewModel.db_username)
        }
    }

}