package com.example.rescate_animales.navigation

sealed class Route(val path: String) {
    object Home : Route("home")
    object Publish : Route("publish")
    object Notifications : Route("notifications")
    object Profile : Route("profile")
}
