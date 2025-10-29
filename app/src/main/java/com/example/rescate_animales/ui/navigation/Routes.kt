package com.example.rescate_animales.navigation

// Route.kt
sealed class Route(val path: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Search : Route("search")
    object Publish : Route("publish")
    object Notifications : Route("notifications")
    object Profile : Route("profile")
    object AnimalDetail : Route("animal_detail") // Solo el path base
    object RecoverPassword : Route("forgot_password")




    // Función helper para crear la ruta completa
    fun createRoute(vararg args: String): String {
        return if (args.isNotEmpty()) {
            "$path/${args.joinToString("/")}"
        } else {
            path
        }
    }
}