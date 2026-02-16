package com.project.neuronexus.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.project.neuronexus.ui.theme.LavenderShell
import com.project.neuronexus.ui.theme.Ink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceTaskScreen(viewModel: VoiceTaskViewModel) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.startTask()
    }

    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("NeuroNexus") }
//            )
//        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LavenderShell)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = state.status, color = Ink)

            state.result?.let {
                Text("Score: ${it.score}", color = Ink)
            }

            Button(onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.RECORD_AUDIO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                } else {
                    viewModel.startTask()
                }
            }) {
                Text("Start Voice Task")
            }
        }
    }
}
