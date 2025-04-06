package com.kevinhomorales.devlokosonlinedocjetpackcompose.onlinedoc.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.firebase.FirebaseApp
import com.kevinhomorales.devlokosonlinedocjetpackcompose.onlinedoc.viewmodel.OnlineDocViewModel

class OnlineDocActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = remember { OnlineDocViewModel() }
            val content by viewModel.content.collectAsState()
            val viewers by viewModel.activeViewers.collectAsState()
            val notification by viewModel.notificationMessage.collectAsState()

            val lifecycle = LocalLifecycleOwner.current.lifecycle
            DisposableEffect(lifecycle) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_DESTROY) {
                        viewModel.removeViewer()
                    }
                }
                lifecycle.addObserver(observer)
                onDispose {
                    lifecycle.removeObserver(observer)
                }
            }
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text("Editor Colaborativo", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("👥 Usuarios viendo esta vista: $viewers", style = MaterialTheme.typography.bodySmall)

                    notification?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it,
                            modifier = Modifier
                                .background(Color.Yellow)
                                .padding(8.dp),
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    BasicTextField(
                        value = content,
                        onValueChange = viewModel::updateContent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color(0xFFEFEFEF))
                            .padding(12.dp),
                        textStyle = TextStyle.Default.copy(color = Color.Black)
                    )
                }
            }
        }
    }
}