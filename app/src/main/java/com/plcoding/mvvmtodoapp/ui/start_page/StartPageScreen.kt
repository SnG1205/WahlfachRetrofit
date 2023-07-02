package com.plcoding.mvvmtodoapp.ui.start_page


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.mvvmtodoapp.util.UiEvent
import kotlinx.coroutines.flow.collect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.mvvmtodoapp.ui.saved_audios_page.SavedAudioItem

@Composable
fun StartPageScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: StartPageViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }

    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
            ) {
        OutlinedButton(onClick = { viewModel.onEvent(StartPageEvent.OnLoginClick) },
        Modifier.size(200.dp, 40.dp)) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedButton(onClick = { viewModel.onEvent(StartPageEvent.OnRegisterClick) },
            Modifier.size(200.dp, 40.dp)) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedButton(onClick = { viewModel.onEvent(StartPageEvent.OnGuestClick) },
            Modifier.size(200.dp, 40.dp)) {
            Text(text = "Continue as  Guest")
        }

        /*Text(text = viewModel.json)
        Text(text = viewModel.messages.size.toString())*/
    }


}