package com.example.rescate_animales.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rescate_animales.ui.screens.*
import androidx.compose.foundation.clickable
@Composable
fun AppNavGraph(
    navController: NavHostController,
    onNavigateToCamera: () -> Unit,
    onNavigateToGallery: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.path  // 👈 Login como inicio
    ) {
        // Login
        composable(Route.Login.path) {
            LoginScreen(navController = navController)
        }

        // Registro (⚠️ SIN onRegister)
        composable(Route.Register.path) {
            CreateAccountScreen(navController = navController)
        }

        // Home
        composable(Route.Home.path) {
            HomeScreen(navController = navController)
        }

        // Búsqueda
        composable(Route.Search.path) {
            SearchScreen(navController = navController)
        }

        // Reportar / Publicar
        composable(Route.Publish.path) {
            ReportScreen(navController = navController)
        }

        // Notificaciones
        composable(Route.Notifications.path) {
            NotificationsScreen(navController = navController)
        }


        // Perfil
        composable(Route.Profile.path) {
            UserProfile(
                navController = navController,
                onNavigateToCamera = onNavigateToCamera,
                onNavigateToGallery = onNavigateToGallery
            )
        }
        composable(Route.RecoverPassword.path) {
            RecoverPasswordScreen(navController)
        }


        // Detalle de mascota
        composable("${Route.AnimalDetail.path}/{animalId}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalId")
            AnimalDetailScreen(
                navController = navController,
                animalId = animalId
            )
        }
    }
}
