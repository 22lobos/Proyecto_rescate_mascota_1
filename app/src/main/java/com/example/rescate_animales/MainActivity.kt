package com.example.rescate_animales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.rescate_animales.navigation.AppNavGraph
import com.example.rescate_animales.ui.components.BottomBar
import com.example.rescate_animales.ui.theme.Rescate_AnimalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Rescate_AnimalesTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController) } // ✅ pasamos el controlador aquí
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
