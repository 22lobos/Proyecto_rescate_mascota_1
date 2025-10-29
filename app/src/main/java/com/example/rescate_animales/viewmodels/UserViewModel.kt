package com.example.rescate_animales.viewmodels  // ← Package diferente

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun updateProfileImage(imageUri: Uri?) {
        viewModelScope.launch {
            _userState.value = _userState.value.copy(
                profileImageUri = imageUri
            )
        }
    }

    fun updateUserInfo(
        nombre: String? = null,
        email: String? = null,
        ubicacion: String? = null,
        telefono: String? = null
    ) {
        viewModelScope.launch {
            val currentState = _userState.value
            _userState.value = currentState.copy(
                nombre = nombre ?: currentState.nombre,
                email = email ?: currentState.email,
                ubicacion = ubicacion ?: currentState.ubicacion,
                telefono = telefono ?: currentState.telefono
            )
        }
    }
}

data class UserState(
    val nombre: String = "Cristopher Lobos",
    val email: String = "cristopher@example.com",
    val ubicacion: String = "Santiago, Chile",
    val telefono: String = "+56 9 1234 5678",
    val profileImageUri: Uri? = null
)