package com.plcoding.mvvmtodoapp.ui.register_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.ui.add_edit_todo.AddEditTodoViewModel
import com.plcoding.mvvmtodoapp.ui.start_page.StartPageEvent
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun RegisterPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: RegisterPageViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
        Arrangement.Center,
    ) {
        Text(text = "Welcome to  Register Page. Please fill the fields below in order to create new account")

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            value = viewModel.username ,
            onValueChange = {
                viewModel.onEvent(RegisterPageEvent.OnUsernameChange(it))
            },
            placeholder = {
                Text(text = "username")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            value = viewModel.password ,
            onValueChange = {
                viewModel.onEvent(RegisterPageEvent.OnPasswordChange(it))
            },
            placeholder = {
                Text(text = "password")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(onClick = {
            viewModel.onEvent(RegisterPageEvent.OnCreateClick)
        }) {
            Text(text = "Create Account")
        }

    }
}
