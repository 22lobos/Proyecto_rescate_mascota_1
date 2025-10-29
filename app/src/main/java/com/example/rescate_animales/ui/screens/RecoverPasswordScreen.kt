package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // ⬅ si marca error, agrega la dependencia indicada abajo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rescate_animales.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    onSendReset: (String) -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    var info by rememberSaveable { mutableStateOf<String?>(null) }
    var loading by rememberSaveable { mutableStateOf(false) }

    fun isValidEmail(s: String): Boolean =
        Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""").matches(s.trim())

    Scaffold(
        topBar = {
            // ------- Opción A: con ícono (requiere material-icons-extended) -------
            TopAppBar(
                title = { Text("Recuperar contraseña") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Ingresa tu correo y te enviaremos un enlace o código para restablecer tu contraseña.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    error = null
                    info = null
                },
                label = { Text("Correo electrónico") },
                singleLine = true,
                isError = error != null,
                supportingText = { if (error != null) Text(error!!) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (!isValidEmail(email)) {
                        error = "Email no válido"
                        info = null
                        return@Button
                    }
                    error = null
                    loading = true
                    try {
                        onSendReset(email.trim())
                        info = "Si el correo existe, enviaremos instrucciones para restablecer la contraseña."
                    } finally {
                        loading = false
                    }
                },

                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(if (loading) "Enviando..." else "Enviar instrucciones")
            }



            if (info != null) {
                Spacer(Modifier.height(12.dp))
                Text(info!!, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate(Route.Login.path) }) {
                Text("Volver a iniciar sesión")
            }
        }
    }
}
