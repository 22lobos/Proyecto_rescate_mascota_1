@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable   // ✅ IMPORT CLAVE
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rescate_animales.R
import com.example.rescate_animales.navigation.Route

@Composable
fun CreateAccountScreen(
    navController: NavController,
    onRegister: (RegisterUiData) -> Unit = {}
) {
    val focus = LocalFocusManager.current

    // ✅ Cambiados a rememberSaveable para persistir en rotación
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var comuna by rememberSaveable { mutableStateOf("") }
    var region by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var pass2 by rememberSaveable { mutableStateOf("") }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var showPass2 by rememberSaveable { mutableStateOf(false) }
    var visibilidad by rememberSaveable { mutableStateOf(Visibilidad.PÚBLICO) }

    // ✅ Errores también persistentes
    var errEmail by rememberSaveable { mutableStateOf<String?>(null) }
    var errPass by rememberSaveable { mutableStateOf<String?>(null) }
    var errPass2 by rememberSaveable { mutableStateOf<String?>(null) }
    var errNombre by rememberSaveable { mutableStateOf<String?>(null) }

    fun validar(): Boolean {
        errNombre = if (nombre.trim().isEmpty()) "Requerido" else null
        val emailOk = EMAIL_REGEX.matches(email.trim())
        errEmail = if (!emailOk) "Email no válido" else null
        val passOk = pass.length >= 6
        errPass = if (!passOk) "Mínimo 6 caracteres" else null
        val same = pass == pass2
        errPass2 = if (!same) "Las contraseñas no coinciden" else null
        return errNombre == null && errEmail == null && errPass == null && errPass2 == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.logo_pet_rescue),
                contentDescription = "Logo",
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("PET RESCUE", style = MaterialTheme.typography.titleLarge)
            Text(
                "Crear cuenta",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre") },
                isError = errNombre != null,
                supportingText = { if (errNombre != null) Text(errNombre!!) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            // Apellido
            OutlinedTextField(
                value = apellido, onValueChange = { apellido = it },
                label = { Text("Apellido") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            // Email
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                isError = errEmail != null,
                supportingText = { if (errEmail != null) Text(errEmail!!) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Teléfono
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it.filter { c -> c.isDigit() || c == '+' } },
                label = { Text("Teléfono") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Comuna y Región
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = comuna, onValueChange = { comuna = it },
                    label = { Text("Comuna") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = region, onValueChange = { region = it },
                    label = { Text("Región") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            // Contraseña
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Contraseña") },
                isError = errPass != null,
                supportingText = {
                    if (errPass != null) Text(errPass!!) else Text("Mín. 6 caracteres")
                },
                singleLine = true,
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Ver"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Confirmar contraseña
            OutlinedTextField(
                value = pass2, onValueChange = { pass2 = it },
                label = { Text("Confirmar contraseña") },
                isError = errPass2 != null,
                supportingText = { if (errPass2 != null) Text(errPass2!!) },
                singleLine = true,
                visualTransformation = if (showPass2) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass2 = !showPass2 }) {
                        Icon(
                            imageVector = if (showPass2) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Ver"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = { focus.clearFocus() }),
                modifier = Modifier.fillMaxWidth()
            )

            // Visibilidad (chips)
            Spacer(Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = visibilidad.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Visibilidad del perfil") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) }
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = { visibilidad = Visibilidad.PÚBLICO },
                    label = { Text("Público") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (visibilidad == Visibilidad.PÚBLICO)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    )
                )
                AssistChip(
                    onClick = { visibilidad = Visibilidad.PRIVADO },
                    label = { Text("Privado") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (visibilidad == Visibilidad.PRIVADO)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    )
                )
                AssistChip(
                    onClick = { visibilidad = Visibilidad.SÓLO_CONTACTOS },
                    label = { Text("Sólo contactos") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (visibilidad == Visibilidad.SÓLO_CONTACTOS)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    )
                )
            }

            Spacer(Modifier.height(18.dp))
            Button(
                onClick = {
                    if (validar()) {
                        onRegister(
                            RegisterUiData(
                                nombre = nombre.trim(),
                                apellido = apellido.trim(),
                                email = email.trim(),
                                telefono = telefono.trim(),
                                comuna = comuna.trim(),
                                region = region.trim(),
                                password = pass,
                                visibilidad = visibilidad
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.large
            ) { Text("Crear cuenta") }

            TextButton(onClick = { navController.navigate(Route.Login.path) }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

private val EMAIL_REGEX =
    Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

data class RegisterUiData(
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val comuna: String,
    val region: String,
    val password: String,
    val visibilidad: Visibilidad
)

enum class Visibilidad(val label: String) {
    PÚBLICO("Público"),
    PRIVADO("Privado"),
    SÓLO_CONTACTOS("Sólo contactos")
}
