package com.example.rescate_animales.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavController) {
    var reportType by rememberSaveable { mutableStateOf(ReportType.LOST) }
    var petName by rememberSaveable { mutableStateOf("") }
    var petSpecies by rememberSaveable { mutableStateOf("") }
    var petBreed by rememberSaveable { mutableStateOf("") }
    var petColor by rememberSaveable { mutableStateOf("") }
    var petSize by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var contactPhone by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reportar Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tipo de Reporte
            ReportTypeSection(
                selectedType = reportType,
                onTypeSelected = { reportType = it }
            )

            // Foto de la Mascota
            PhotoSection(
                selectedImageUri = selectedImageUri,
                onImageSelected = { uri -> selectedImageUri = uri },
                onRemoveImage = { selectedImageUri = null }
            )

            // Información Básica
            BasicInfoSection(
                petName = petName,
                onPetNameChange = { petName = it },
                petSpecies = petSpecies,
                onPetSpeciesSelected = { petSpecies = it },
                petBreed = petBreed,
                onPetBreedChange = { petBreed = it },
                petColor = petColor,
                onPetColorChange = { petColor = it },
                petSize = petSize,
                onPetSizeSelected = { petSize = it }
            )

            // Descripción
            DescriptionSection(
                description = description,
                onDescriptionChange = { description = it }
            )

            // Ubicación y Contacto
            LocationContactSection(
                location = location,
                onLocationChange = { location = it },
                contactPhone = contactPhone,
                onContactPhoneChange = { contactPhone = it }
            )

            // Botón de Publicar
            Button(
                onClick = {
                    // Lógica para publicar el reporte
                    // Después de publicar, volver al home
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = isFormValid(
                    petName, petSpecies, location, contactPhone, selectedImageUri
                )
            ) {
                Text("Publicar Reporte", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ReportTypeSection(
    selectedType: ReportType,
    onTypeSelected: (ReportType) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Tipo de Reporte",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ReportTypeOption(
                    type = ReportType.LOST,
                    selectedType = selectedType,
                    onSelected = onTypeSelected,
                    modifier = Modifier.weight(1f)
                )
                ReportTypeOption(
                    type = ReportType.FOUND,
                    selectedType = selectedType,
                    onSelected = onTypeSelected,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ReportTypeOption(
    type: ReportType,
    selectedType: ReportType,
    onSelected: (ReportType) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = type == selectedType

    Surface(
        modifier = modifier
            .selectable(
                selected = isSelected,
                onClick = { onSelected(type) }
            ),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) type.color else MaterialTheme.colorScheme.surfaceVariant,
        border = if (isSelected) null else CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = type.displayName,
                modifier = Modifier.size(32.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = type.displayName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun PhotoSection(
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onRemoveImage: () -> Unit
) {
    val context = LocalContext.current

    // Launcher para seleccionar imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onImageSelected(uri)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Foto de la Mascota",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (selectedImageUri != null) {
                // Mostrar imagen seleccionada
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context)
                                .data(selectedImageUri)
                                .build()
                        ),
                        contentDescription = "Foto de la mascota",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Botón para eliminar foto
                    IconButton(
                        onClick = onRemoveImage,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Eliminar foto",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón para cambiar foto
                OutlinedButton(
                    onClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cambiar Foto")
                }
            } else {
                // Botón para agregar foto
                OutlinedButton(
                    onClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = "Agregar foto",
                            modifier = Modifier.size(32.dp)
                        )
                        Text("Seleccionar de Galería")
                        Text(
                            text = "Recomendado para mejores resultados",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BasicInfoSection(
    petName: String,
    onPetNameChange: (String) -> Unit,
    petSpecies: String,
    onPetSpeciesSelected: (String) -> Unit,
    petBreed: String,
    onPetBreedChange: (String) -> Unit,
    petColor: String,
    onPetColorChange: (String) -> Unit,
    petSize: String,
    onPetSizeSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información Básica",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = petName,
                    onValueChange = onPetNameChange,
                    label = { Text("Nombre de la mascota") },
                    leadingIcon = {
                        Icon(Icons.Default.Pets, contentDescription = "Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                SpeciesDropdown(
                    selectedSpecies = petSpecies,
                    onSpeciesSelected = onPetSpeciesSelected
                )

                OutlinedTextField(
                    value = petBreed,
                    onValueChange = onPetBreedChange,
                    label = { Text("Raza (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = petColor,
                    onValueChange = onPetColorChange,
                    label = { Text("Color") },
                    leadingIcon = {
                        Icon(Icons.Default.Palette, contentDescription = "Color")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                SizeDropdown(
                    selectedSize = petSize,
                    onSizeSelected = onPetSizeSelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesDropdown(
    selectedSpecies: String,
    onSpeciesSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val speciesOptions = listOf("Perro", "Gato", "Otro")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedSpecies,
            onValueChange = { },
            readOnly = true,
            label = { Text("Especie") },
            leadingIcon = {
                Icon(Icons.Default.Category, contentDescription = "Especie")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            speciesOptions.forEach { species ->
                DropdownMenuItem(
                    text = { Text(species) },
                    onClick = {
                        onSpeciesSelected(species)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeDropdown(
    selectedSize: String,
    onSizeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val sizeOptions = listOf("Pequeño", "Mediano", "Grande")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedSize,
            onValueChange = { },
            readOnly = true,
            label = { Text("Tamaño") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sizeOptions.forEach { size ->
                DropdownMenuItem(
                    text = { Text(size) },
                    onClick = {
                        onSizeSelected(size)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DescriptionSection(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Describe a la mascota y circunstancias...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )
        }
    }
}

@Composable
fun LocationContactSection(
    location: String,
    onLocationChange: (String) -> Unit,
    contactPhone: String,
    onContactPhoneChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ubicación y Contacto",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = location,
                    onValueChange = onLocationChange,
                    label = { Text("Ubicación donde se perdió/encontró") },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = "Ubicación")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = contactPhone,
                    onValueChange = onContactPhoneChange,
                    label = { Text("Teléfono de contacto") },
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }
    }
}

// Función para validar el formulario
private fun isFormValid(
    petName: String,
    petSpecies: String,
    location: String,
    contactPhone: String,
    selectedImageUri: Uri?
): Boolean {
    return petName.isNotBlank() &&
            petSpecies.isNotBlank() &&
            location.isNotBlank() &&
            contactPhone.isNotBlank() &&
            selectedImageUri != null
}

// Enums y data classes
enum class ReportType(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    LOST(
        displayName = "Perdida",
        icon = Icons.Default.Warning,
        color = Color(0xFFFFA000)
    ),
    FOUND(
        displayName = "Encontrada",
        icon = Icons.Default.CheckCircle,
        color = Color(0xFF4CAF50)
    )
}