package com.example.rescate_animales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rescate_animales.navigation.AppNavGraph
import com.example.rescate_animales.navigation.Route
import com.example.rescate_animales.ui.components.BottomBar
import com.example.rescate_animales.ui.theme.Rescate_AnimalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Rescate_AnimalesTheme {
                val navController = rememberNavController()

                // Rutas donde SÍ queremos mostrar la BottomBar
                val routesWithBar = setOf(
                    Route.Home.path,
                    Route.Publish.path,
                    Route.Notifications.path,
                    Route.Profile.path
                )

                //  Detecta la ruta actual
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute in routesWithBar) {
                            BottomBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
