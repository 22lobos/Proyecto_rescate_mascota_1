package com.example.rescate_animales.data.local

import android.content.Context
import com.example.rescate_animales.data.local.database.AppDatabase
import com.example.rescate_animales.repository.AnimalRepository
import com.example.rescate_animales.repository.NotificationRepository
import com.example.rescate_animales.repository.UserRepository

object ServiceLocator {
    @Volatile private var initialized = false

    lateinit var animals: AnimalRepository
        private set
    lateinit var users: UserRepository
        private set
    lateinit var notifications: NotificationRepository
        private set

    fun init(ctx: Context) {
        if (initialized) return
        val db = AppDatabase.get(ctx)
        animals = AnimalRepository(db.animalDao())
        users = UserRepository(ctx, db.userDao())
        notifications = NotificationRepository(db.notificationDao())
        initialized = true
        println("✅ ServiceLocator inicializado correctamente")
    }
}
