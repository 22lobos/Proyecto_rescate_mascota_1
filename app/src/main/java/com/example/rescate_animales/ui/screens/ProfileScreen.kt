package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    var nombre by remember { mutableStateOf("Cristofer Lobos") }
    var email by remember { mutableStateOf("cristof@example.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { /* guardar cambios */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Cambios")
        }
    }
}
