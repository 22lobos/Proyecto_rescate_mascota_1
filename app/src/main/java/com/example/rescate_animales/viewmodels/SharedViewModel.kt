package com.example.rescate_animales.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    // Estado global de la aplicación
    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    // Función para actualizar el estado de carga
    fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                isLoading = isLoading
            )
        }
    }

    // Función para actualizar el usuario logueado
    fun setUserLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                userLoggedIn = isLoggedIn
            )
        }
    }

    // Función para actualizar la pantalla actual
    fun setCurrentScreen(screen: String) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                currentScreen = screen
            )
        }
    }

    // Función para manejar animales favoritos
    fun addFavoriteAnimal(animal: Animal) {
        viewModelScope.launch {
            val currentFavorites = _appState.value.favoriteAnimals.toMutableList()
            currentFavorites.add(animal)
            _appState.value = _appState.value.copy(
                favoriteAnimals = currentFavorites
            )
        }
    }

    // Función para limpiar el estado
    fun clearAppState() {
        viewModelScope.launch {
            _appState.value = AppState()
        }
    }
}

// Data class para el estado global
data class AppState(
    // Estado de autenticación
    val userLoggedIn: Boolean = false,
    val currentUser: User? = null,
    val userToken: String? = null,

    // Estado de UI
    val isLoading: Boolean = false,
    val currentScreen: String = "home",

    // Configuración
    val darkMode: Boolean = false,
    val language: String = "es",
    val notificationsEnabled: Boolean = true,

    // Datos de la aplicación
    val favoriteAnimals: List<Animal> = emptyList(),
    val selectedAnimal: Animal? = null
)

// Modelos de datos (debes crear estos en otra carpeta)
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: String?
)

data class Animal(
    val id: String,
    val name: String,
    val type: String,
    val imageUrl: String?
)