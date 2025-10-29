package com.example.rescate_animales.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rescate_animales.navigation.Route

@Composable
fun BottomBar(navController: NavController) {
    // Definir los items de la barra inferior - ESTRUCTURA CORREGIDA
    val items = listOf(
        BottomBarItem(Route.Home, Icons.Default.Home, "Inicio"),
        BottomBarItem(Route.Search, Icons.Default.Search, "Buscar"),
        BottomBarItem(Route.Publish, Icons.Default.AddCircle, "Reportar"),
        BottomBarItem(Route.Profile, Icons.Default.Person, "Perfil")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route.path } == true,
                onClick = {
                    navController.navigate(item.route.path) {
                        // Configuración de navegación inteligente
                        popUpTo(Route.Home.path) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}

// Data class para representar cada item del BottomBar
data class BottomBarItem(
    val route: Route,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)