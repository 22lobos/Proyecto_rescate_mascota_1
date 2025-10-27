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
        startDestination = Route.Login.path
    ) {
        composable(Route.Login.path) {
            LoginScreen(navController)
        }
        composable(Route.Home.path) {
            HomeScreen(navController)
        }
        composable(Route.Publish.path) {
            PublishScreen()
        }
        composable(Route.Notifications.path) {
            NotificationsScreen()
        }
        composable(Route.Profile.path) {
            ProfileScreen()
        }
        composable(Route.Register.path) {
            CreateAccountScreen(
                navController = navController,
                onRegister = { data ->
                    navController.navigate(Route.Login.path) {
                        popUpTo(Route.Register.path) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        // ✅ Recuperar contraseña
        composable(Route.Recover.path) {
            RecoverPasswordScreen(
                navController = navController,
                onSendReset = { email ->
                    // TODO: conectar a tu backend / ViewModel
                }
            )
        }
    }
}
