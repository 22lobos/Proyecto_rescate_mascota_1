package com.example.rescate_animales.repository

import com.example.rescate_animales.data.local.dao.NotificationDao
import com.example.rescate_animales.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(
    private val dao: NotificationDao
) {
    fun list(): Flow<List<NotificationEntity>> = dao.getAll()

    suspend fun seedDemo() {
        val demo = listOf(
            NotificationEntity(
                id = "n1",
                title = "Bienvenido",
                message = "Gracias por usar Pet Rescue",
                timestamp = System.currentTimeMillis()
            )
        )
        dao.insertAll(demo)
    }
}
