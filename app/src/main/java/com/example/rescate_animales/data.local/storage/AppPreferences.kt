package com.example.rescate_animales.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 🔹 Extensión de contexto para crear el DataStore
private val Context.dataStore by preferencesDataStore("app_prefs")

object AppPreferences {

    // 🔹 Claves de preferencias
    private val KEY_USER_NAME = stringPreferencesKey("user_name")
    private val KEY_USER_EMAIL = stringPreferencesKey("user_email")

    // 🔹 Obtener nombre guardado
    fun userNameFlow(ctx: Context): Flow<String> =
        ctx.dataStore.data.map { prefs ->
            prefs[KEY_USER_NAME] ?: ""
        }

    // 🔹 Obtener email guardado
    fun userEmailFlow(ctx: Context): Flow<String> =
        ctx.dataStore.data.map { prefs ->
            prefs[KEY_USER_EMAIL] ?: ""
        }

    // 🔹 Guardar usuario (nombre y email)
    suspend fun setUser(ctx: Context, name: String, email: String) {
        ctx.dataStore.edit { prefs ->
            prefs[KEY_USER_NAME] = name
            prefs[KEY_USER_EMAIL] = email
        }
    }

    // 🔹 Borrar usuario (logout)
    suspend fun clearUser(ctx: Context) {
        ctx.dataStore.edit { prefs ->
            prefs.remove(KEY_USER_NAME)
            prefs.remove(KEY_USER_EMAIL)
        }
    }
}
