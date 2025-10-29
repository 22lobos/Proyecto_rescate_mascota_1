package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController? = null) {
    var notifications by remember { mutableStateOf(sampleNotifications) }
    val isPreview = LocalInspectionMode.current // Detecta si estamos en modo preview

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notificaciones") },
                navigationIcon = {
                    // Mostrar flecha si hay navController O si es un preview
                    if (navController != null || isPreview) {
                        IconButton(onClick = {
                            navController?.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Marcar todas como leídas
                        notifications = notifications.map { it.copy(isRead = true) }
                    }) {
                        Icon(Icons.Filled.MarkEmailRead, contentDescription = "Marcar todas como leídas")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            EmptyNotificationsState()
        } else {
            NotificationsList(
                notifications = notifications,
                onNotificationClick = { notification ->
                    // Marcar como leída al hacer clic
                    notifications = notifications.map {
                        if (it.id == notification.id) it.copy(isRead = true) else it
                    }

                    // Navegar según el tipo de notificación
                    when (notification.type) {
                        NotificationType.MATCH -> {
                            // Navegar a detalles de la mascota coincidente
                            val animalId = "123" // Aquí deberías obtener el ID real de la notificación
                            navController?.navigate("animal_detail/$animalId")
                        }
                        NotificationType.MESSAGE -> {
                            // Navegar a chat o mensajes
                            // navController?.navigate(Route.Chat.path)
                        }
                        else -> {
                            // Para otros tipos, solo marcar como leída
                        }
                    }
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun NotificationsList(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications, key = { it.id }) { notification ->
            NotificationItem(
                notification = notification,
                onClick = { onNotificationClick(notification) }
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icono de la notificación
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(notification.type.color)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.type.icon,
                    contentDescription = notification.type.displayName,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contenido de la notificación
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            // Indicador de no leído
            if (!notification.isRead) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun EmptyNotificationsState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.NotificationsOff,
            contentDescription = "Sin notificaciones",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No hay notificaciones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Te avisaremos cuando tengas nuevas actualizaciones",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// Data classes y enums
data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {
    MATCH(
        displayName = "Coincidencia",
        icon = Icons.Filled.Pets,
        color = Color(0xFF4CAF50) // Green
    ),
    MESSAGE(
        displayName = "Mensaje",
        icon = Icons.Filled.Message,
        color = Color(0xFF2196F3) // Blue
    ),
    UPDATE(
        displayName = "Actualización",
        icon = Icons.Filled.Update,
        color = Color(0xFFFF9800) // Orange
    ),
    SUCCESS(
        displayName = "Éxito",
        icon = Icons.Filled.CheckCircle,
        color = Color(0xFF4CAF50) // Green
    ),
    WARNING(
        displayName = "Alerta",
        icon = Icons.Filled.Warning,
        color = Color(0xFFF44336) // Red
    )
}

// Datos de ejemplo
private val sampleNotifications = listOf(
    Notification(
        id = 1,
        title = "¡Posible coincidencia encontrada!",
        message = "Hemos encontrado una mascota que coincide con la descripción de tu reporte perdido",
        timestamp = "Hace 2 horas",
        type = NotificationType.MATCH,
        isRead = false
    ),
    Notification(
        id = 2,
        title = "Nuevo mensaje",
        message = "Juan te ha enviado un mensaje sobre tu mascota encontrada",
        timestamp = "Hace 5 horas",
        type = NotificationType.MESSAGE,
        isRead = true
    ),
    Notification(
        id = 3,
        title = "Reporte actualizado",
        message = "Tu reporte de mascota perdida ha sido actualizado con nueva información",
        timestamp = "Ayer",
        type = NotificationType.UPDATE,
        isRead = true
    ),
    Notification(
        id = 4,
        title = "¡Mascota reunida!",
        message = "Felicidades, tu mascota ha sido reunida contigo exitosamente",
        timestamp = "Hace 2 días",
        type = NotificationType.SUCCESS,
        isRead = true
    ),
    Notification(
        id = 5,
        title = "Recordatorio importante",
        message = "Tu reporte expirará en 3 días. ¿Deseas renovarlo?",
        timestamp = "Hace 3 días",
        type = NotificationType.WARNING,
        isRead = false
    )
)

// Preview único y funcional
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    MaterialTheme {
        NotificationsScreen() // Ahora mostrará la flecha en el preview
    }
}