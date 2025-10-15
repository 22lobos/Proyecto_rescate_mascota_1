package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rescate_animales.R
import com.example.rescate_animales.navigation.Route

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))

        // Logo (asegúrate de tener res/drawable/logo_pet_rescue.png)
        Image(
            painter = painterResource(id = R.drawable.logo_pet_rescue),
            contentDescription = "Logo Pet Rescue",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 8.dp)
        )

        Text("PET RESCUE", style = MaterialTheme.typography.titleLarge)
        Text("Iniciar sesión", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        // Correo (sin KeyboardOptions para evitar errores)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            placeholder = { Text("example@email.com") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Contraseña con botón de texto para mostrar/ocultar (sin íconos)
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { passVisible = !passVisible }) {
                    Text(if (passVisible) "Ocultar" else "Mostrar")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Spacer(Modifier.height(6.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || pass.isBlank()) {
                    error = "Completa correo y contraseña"
                } else {
                    error = null
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(Route.Register.path) }) {
            Text("Crear cuenta")
        }

        TextButton(onClick = { /* TODO: navegar a Recuperar contraseña */ }) {
            Text("¿Olvidó la contraseña?")
        }
    }
}
