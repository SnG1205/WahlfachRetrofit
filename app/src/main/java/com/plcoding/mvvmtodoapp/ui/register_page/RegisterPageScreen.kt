package com.plcoding.mvvmtodoapp.ui.register_page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.ui.not_logged_page.NotLoggedPageEvent
import com.plcoding.mvvmtodoapp.ui.start_page.StartPageEvent
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: RegisterPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title =
            { Text(text = "Register Page") }
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
            Text(text = "Welcome to  Register Page. Please fill the fields below in order to create new account")

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.username,
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
                value = viewModel.password,
                onValueChange = {
                    viewModel.onEvent(RegisterPageEvent.OnPasswordChange(it))
                },
                placeholder = {
                    Text(text = "password")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedButton(
                onClick = {
                    viewModel.onEvent(RegisterPageEvent.OnCreateClick)
                },
                Modifier.size(160.dp, 40.dp)
            ) {
                Text(text = "Create Account")
            }

        }
    }

}
