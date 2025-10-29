package com.example.rescate_animales.repository

import android.content.Context
import com.example.rescate_animales.data.local.dao.UserDao
import com.example.rescate_animales.data.local.entity.UserEntity
import com.example.rescate_animales.data.local.storage.AppPreferences
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val ctx: Context,
    private val dao: UserDao
) {
    fun currentUser(): Flow<UserEntity?> = dao.getCurrent()

    /**
     * Login de ejemplo: acepta cualquier contraseña >= 6.
     * Guarda el usuario en Room y en DataStore para mostrar "Hola, {nombre}".
     */
    suspend fun login(email: String, password: String): Result<UserEntity> {
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos 6 caracteres"))
        }
        // Demo: crea un usuario simple
        val user = UserEntity(
            id = "u1",
            name = "Cristofer",
            email = email.trim()
        )
        dao.insert(user)
        AppPreferences.setUser(ctx, name = user.name, email = user.email)
        return Result.success(user)
    }

    suspend fun logout() {
        dao.clear()
        AppPreferences.clearUser(ctx)
    }
}
