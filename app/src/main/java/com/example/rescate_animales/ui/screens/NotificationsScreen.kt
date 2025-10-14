package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotificationsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Notificaciones", style = MaterialTheme.typography.titleLarge)
        repeat(5) {
            Card {
                Column(Modifier.padding(12.dp)) {
                    Text("Nueva alerta #$it")
                    Text("Alguien comentó una publicación tuya")
                }
            }
        }
    }
}
