package com.example.rescate_animales.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rescate_animales.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    navController: NavController,
    onNavigateToCamera: () -> Unit,
    onNavigateToGallery: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("Cristopher Lobos") }
    var email by rememberSaveable { mutableStateOf("cristopher@example.com") }
    var ubicacion by rememberSaveable { mutableStateOf("Santiago, Chile") }
    var telefono by rememberSaveable { mutableStateOf("+56 9 1234 5678") }
    var profileImageUri by rememberSaveable { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header del Perfil con Foto
        ProfileHeader(
            profileImageUri = profileImageUri,
            onFotoClick = {
                showImagePickerDialog(
                    context = context,
                    onCameraSelected = onNavigateToCamera,
                    onGallerySelected = onNavigateToGallery
                )
            }
        )

        // Información Personal
        PersonalInfoSection(
            nombre = nombre,
            email = email,
            ubicacion = ubicacion,
            telefono = telefono,
            onNombreChange = { nuevoNombre -> nombre = nuevoNombre },
            onEmailChange = { nuevoEmail -> email = nuevoEmail },
            onUbicacionChange = { nuevaUbicacion -> ubicacion = nuevaUbicacion },
            onTelefonoChange = { nuevoTelefono -> telefono = nuevoTelefono }
        )

        // Acciones
        ActionsSection()

        // Botón Cerrar Sesión
        OutlinedButton(
            onClick = { /* cerrar sesion */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun ProfileHeader(
    profileImageUri: String?,
    onFotoClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar con imagen seleccionable
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { onFotoClick() }
        ) {
            if (!profileImageUri.isNullOrEmpty()) {
                // Imagen de perfil cargada
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Avatar por defecto
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Icono de cámara para indicar que es editable
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Cambiar foto",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cristopher Lobos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "cristopher@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Texto indicativo
        Text(
            text = "Toca la foto para cambiar",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun PersonalInfoSection(
    nombre: String,
    email: String,
    ubicacion: String,
    telefono: String,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUbicacionChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit
) {
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = { isEditing = !isEditing }) {
                    Text(if (isEditing) "Cancelar" else "Editar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre completo") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Nombre")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Correo electrónico") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ubicacion,
                    onValueChange = onUbicacionChange,
                    label = { Text("Ubicación") },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = "Ubicación")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = onTelefonoChange,
                    label = { Text("Teléfono") },
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { isEditing = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios")
                }
            } else {
                InfoRow(icon = Icons.Default.Person, text = nombre)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(icon = Icons.Default.Email, text = email)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(icon = Icons.Default.LocationOn, text = ubicacion)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(icon = Icons.Default.Phone, text = telefono)
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionsSection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Acciones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ActionItem(
                icon = Icons.AutoMirrored.Filled.List,
                text = "Mis Reportes",
                onClick = { /* Navegar a reportes */ }
            )

            ActionItem(
                icon = Icons.Default.Favorite,
                text = "Favoritos Guardados",
                onClick = { /* Navegar a favoritos */ }
            )

            ActionItem(
                icon = Icons.Default.Notifications,
                text = "Notificaciones",
                onClick = { /* Navegar a notificaciones */ }
            )

            ActionItem(
                icon = Icons.Default.Settings,
                text = "Configuración",
                onClick = { /* Navegar a configuración */ }
            )
        }
    }
}

@Composable
fun ActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Ir",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// Función CORREGIDA para mostrar el diálogo de selección de imagen
fun showImagePickerDialog(
    context: Context,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit
) {
    val options = arrayOf("Tomar foto", "Elegir de galería", "Cancelar")

    android.app.AlertDialog.Builder(context) // ✅ Cambiado a android.app.AlertDialog
        .setTitle("Seleccionar imagen de perfil")
        .setItems(options) { dialog, which ->
            when (which) {
                0 -> onCameraSelected()
                1 -> onGallerySelected()
                2 -> dialog.dismiss() // ✅ dismiss() es correcto
            }
        }
        .show()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfilePreview() {
    // Crea un NavController ficticio para la vista previa
    val navController = androidx.navigation.compose.rememberNavController()

    MaterialTheme {
        UserProfile(
            navController = navController,
            onNavigateToCamera = {},
            onNavigateToGallery = {}
        )
    }
}