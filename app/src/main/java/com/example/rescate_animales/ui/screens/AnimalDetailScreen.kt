package com.example.rescate_animales.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    navController: NavController,
    animalId: String? = null
) {
    val animal = remember(animalId) {
        AnimalDetail(
            id = animalId ?: "1",
            name = "Max",
            type = "Labrador Dorado",
            breed = "Labrador Retriever",
            status = "PERDIDO",
            statusColor = Color(0xFFFFA000),
            location = "Providencia 123, Santiago",
            distance = "2.3 km",
            timeAgo = "Hace 2 horas",
            description = "Max es un labrador dorado muy amigable y juguetón. Fue visto por última vez cerca del Parque Bustamante.",
            contactName = "María González",
            contactPhone = "+56 9 1234 5678",
            contactEmail = "maria@email.com",
            photos = listOf(android.R.drawable.ic_menu_camera)
        )
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles de Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Agregar a favoritos */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorito")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Contactar dueño */ },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Contactar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contactar Dueño")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = animal.photos.first()),
                    contentDescription = "Foto de ${animal.name}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    color = animal.statusColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = animal.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimalBasicInfo(animal = animal)
                AnimalDescription(description = animal.description)
                ContactInfo(animal = animal)
                AnimalCharacteristics(animal = animal)
            }
        }
    }
}

@Composable
fun AnimalBasicInfo(animal: AnimalDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = animal.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(icon = Icons.Default.Pets, text = animal.type, subtitle = "Especie")
                InfoItem(icon = Icons.Default.LocationOn, text = animal.distance, subtitle = "Distancia")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(icon = Icons.Default.Schedule, text = animal.timeAgo, subtitle = "Visto")
                InfoItem(icon = Icons.Default.Place, text = animal.location.split(",").first(), subtitle = "Ubicación")
            }
        }
    }
}

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    subtitle: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = subtitle, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun AnimalDescription(description: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ContactInfo(animal: AnimalDetail) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Información de Contacto", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            ContactItem(Icons.Default.Person, "Dueño", animal.contactName)
            ContactItem(Icons.Default.Phone, "Teléfono", animal.contactPhone, isClickable = true)
            ContactItem(Icons.Default.Email, "Email", animal.contactEmail, isClickable = true)
        }
    }
}

@Composable
fun ContactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String,
    isClickable: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(content, style = MaterialTheme.typography.bodyMedium)
        }
        if (isClickable) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun AnimalCharacteristics(animal: AnimalDetail) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Características", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text("• Raza: ${animal.breed}")
            Text("• Color: Dorado")
            Text("• Tamaño: Mediano")
            Text("• Collar: Azul")
        }
    }
}

data class AnimalDetail(
    val id: String,
    val name: String,
    val type: String,
    val breed: String,
    val status: String,
    val statusColor: Color,
    val location: String,
    val distance: String,
    val timeAgo: String,
    val description: String,
    val contactName: String,
    val contactPhone: String,
    val contactEmail: String,
    val photos: List<Int>
)

@Preview(showBackground = true)
@Composable
fun AnimalDetailScreenPreview() {
    MaterialTheme { AnimalDetailScreen(rememberNavController()) }
}
