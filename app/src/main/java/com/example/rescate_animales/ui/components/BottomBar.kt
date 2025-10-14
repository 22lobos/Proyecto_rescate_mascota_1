package com.example.rescate_animales.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rescate_animales.navigation.Route

@Composable
fun BottomBar(navController: NavController) {   // 👈 Recibe el controlador
    val items = listOf(
        Route.Home to Icons.Default.Home,
        Route.Publish to Icons.Default.AddCircle,
        Route.Notifications to Icons.Default.Notifications,
        Route.Profile to Icons.Default.Person
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { (route, icon) ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == route.path } == true,
                onClick = {
                    navController.navigate(route.path) {
                        popUpTo(Route.Home.path) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = icon, contentDescription = route.path) },
                label = { Text(route.path.replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}
