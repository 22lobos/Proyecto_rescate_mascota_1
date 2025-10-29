package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rescate_animales.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            HomeHeader(
                onSearchClick = { navController.navigateSafe(Route.Search.path) },
                onNotificationsClick = { navController.navigateSafe(Route.Notifications.path) }
            )

            // Search Bar
            SearchBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchClick = { navController.navigateSafe(Route.Search.path) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Urgent Section
                item {
                    UrgentAnimalsSection(navController = navController)
                }

                // Recent Reports Section
                item {
                    RecentReportsSection(navController = navController)
                }
            }
        }

        // FAB Positioned correctly
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            HomeFAB(navController = navController)
        }
    }
}

@Composable
fun HomeHeader(
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mascotas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSearchClick() },
                tint = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificaciones",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNotificationsClick() },
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onSearchClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = if (searchText.isEmpty()) "Buscar mascotas..." else searchText,
                color = if (searchText.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun UrgentAnimalsSection(navController: NavController) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🚨 URGENTE - Cerca de ti",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Ver todo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    // Navegar a lista urgente si existe
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Animal urgente de ejemplo - CORREGIDO
        AnimalCard(
            name = "Max",
            type = "Labrador Dorado",
            status = "PERDIDO",
            location = "Providencia",
            distance = "2.3 km",
            timeAgo = "Hace 2 horas",
            statusColor = Color(0xFFFFA000),
            onCardClick = {
                // CORREGIDO: Navegación exacta como está en NavGraph
                navController.navigateSafe("animal_detail/max_123")
            }
        )
    }
}

@Composable
fun RecentReportsSection(navController: NavController) {
    Column {
        Text(
            text = "Reportes Recientes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val recentAnimals = listOf(
            AnimalData("Luna", "Gato Siamés", "ENCONTRADO", "Las Condes", "4.1 km", "Ayer 15:30", Color(0xFF4CAF50)),
            AnimalData("Toby", "Mestizo", "PERDIDO", "Ñuñoa", "1.2 km", "Hoy 10:15", Color(0xFFFFA000)),
            AnimalData("Bella", "Golden Retriever", "PERDIDO", "Vitacura", "3.7 km", "Hace 5 horas", Color(0xFFFFA000))
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            recentAnimals.forEach { animal ->
                AnimalCard(
                    name = animal.name,
                    type = animal.type,
                    status = animal.status,
                    location = animal.location,
                    distance = animal.distance,
                    timeAgo = animal.timeAgo,
                    statusColor = animal.statusColor,
                    onCardClick = {
                        // CORREGIDO: Navegación exacta como está en NavGraph
                        val animalId = when (animal.name) {
                            "Luna" -> "luna_456"
                            "Toby" -> "toby_789"
                            "Bella" -> "bella_012"
                            else -> "${animal.name.lowercase()}_default"
                        }
                        navController.navigateSafe("animal_detail/$animalId")
                    }
                )
            }
        }
    }
}

@Composable
fun AnimalCard(
    name: String,
    type: String,
    status: String,
    location: String,
    distance: String,
    timeAgo: String,
    statusColor: Color,
    onCardClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header con nombre y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🐕 $name - $type",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Surface(
                    color = statusColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Información de ubicación y tiempo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "📍 $location • $distance",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Clase de datos para información de animales
data class AnimalData(
    val name: String,
    val type: String,
    val status: String,
    val location: String,
    val distance: String,
    val timeAgo: String,
    val statusColor: Color
)

// FAB para crear nuevos reportes
@Composable
fun HomeFAB(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigateSafe(Route.Publish.path) },
        modifier = Modifier.size(56.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Crear publicación",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

// Función de extensión para navegación segura
private fun NavController.navigateSafe(route: String) {
    try {
        println("DEBUG: Intentando navegar a: $route")
        navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    } catch (e: Exception) {
        println("ERROR: No se pudo navegar a $route - ${e.message}")
        e.printStackTrace()
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = androidx.navigation.compose.rememberNavController())
    }
}