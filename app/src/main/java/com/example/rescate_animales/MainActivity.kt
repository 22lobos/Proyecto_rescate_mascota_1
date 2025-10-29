package com.example.rescate_animales

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.rescate_animales.activities.CameraActivity
import com.example.rescate_animales.data.local.ServiceLocator   // ✅ Import agregado

class MainActivity : ComponentActivity() {

    private val cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            println("📸 Foto tomada: $imageUri")
            // Aquí puedes actualizar tu ViewModel con la nueva imagen si lo deseas
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializa base de datos y repositorios (ServiceLocator)
        ServiceLocator.init(applicationContext)

        enableEdgeToEdge()
        setContent {
            Rescate_AnimalesTheme {
                val navController = rememberNavController()

                // Rutas donde se muestra la BottomBar
                val routesWithBar = setOf(
                    Route.Home.path,
                    Route.Search.path,
                    Route.Profile.path
                )

                // Detecta la ruta actual
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
                        AppNavGraph(
                            navController = navController,
                            onNavigateToCamera = {
                                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                                cameraResultLauncher.launch(intent)
                            },
                            onNavigateToGallery = {
                                println("🖼️ Abrir galería - pendiente de implementar")
                            }
                        )
                    }
                }
            }
        }
    }
}
