package com.example.rescate_animales.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rescate_animales.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.path // Pantalla inicial
    ) {
        // Pantalla de inicio de sesión
        composable(Route.Login.path) {
            LoginScreen(navController)
        }

        // Pantalla principal (home)
        composable(Route.Home.path) {
            HomeScreen(navController)
        }

        // Publicaciones
        composable(Route.Publish.path) {
            PublishScreen()
        }

        // Notificaciones
        composable(Route.Notifications.path) {
            NotificationsScreen()
        }

        // Perfil
        composable(Route.Profile.path) {
            ProfileScreen()
        }

        // 👇 Nueva pantalla de creación de cuenta (registro)
        composable(Route.Register.path) {
            CreateAccountScreen(
                navController = navController,
                onRegister = { data ->
                    // Aquí puedes hacer el registro (llamar ViewModel, API, etc.)
                    // Cuando todo salga bien, vuelve al Login:
                    navController.navigate(Route.Login.path) {
                        popUpTo(Route.Register.path) { inclusive = true }
                    }
                }
            )
        }
    }
}
