package com.example.rescate_animales.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rescate_animales.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Home.path) {
        composable(Route.Home.path) { HomeScreen(navController) }
        composable(Route.Publish.path) { PublishScreen() }
        composable(Route.Notifications.path) { NotificationsScreen() }
        composable(Route.Profile.path) { ProfileScreen() }
    }
}
