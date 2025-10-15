package com.example.rescate_animales.navigation

sealed class Route(val path: String) {
    object Login : Route("login")

    data object Register : Route("register")
    object Home : Route("home")
    object Publish : Route("publish")
    object Notifications : Route("notifications")
    object Profile : Route("profile")
}
