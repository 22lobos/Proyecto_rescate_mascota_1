package com.example.rescate_animales.repository

import com.example.rescate_animales.data.local.dao.AnimalDao
import com.example.rescate_animales.data.local.entity.AnimalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimalRepository(
    private val dao: AnimalDao
) {
    /** Lista todo (tu DAO ya expone getAll()) */
    fun listRecent(): Flow<List<AnimalEntity>> = dao.getAll()

    /** Fallback: si no hay search en el DAO, devolvemos todo y filtramos en memoria */
    fun search(query: String): Flow<List<AnimalEntity>> =
        dao.getAll().map { list ->
            val q = query.trim().lowercase()
            if (q.isEmpty()) list
            else list.filter { a ->
                a.name.lowercase().contains(q) ||
                        a.type.lowercase().contains(q) ||
                        (a.location?.lowercase()?.contains(q) == true) ||
                        (a.description?.lowercase()?.contains(q) == true)
            }
        }

    /** Fallback: si no hay getById en el DAO, buscamos sobre el flujo de getAll() */
    fun getById(id: String): Flow<AnimalEntity?> =
        dao.getAll().map { list -> list.find { it.id == id } }

    /** Si tu DAO usa insertAll() en lugar de upsertAll(), llamamos a ese */
    suspend fun upsertAll(items: List<AnimalEntity>) {
        // si tu DAO solo tiene insertAll():
        dao.insertAll(items)
    }

    /** Datos de demo opcionales */
    suspend fun seedDemo() {
        val demo = listOf(
            AnimalEntity(
                id = "max_123", name = "Max", type = "Perro", status = "PERDIDO",
                location = "Providencia", description = "Labrador dorado muy amigable."
            ),
            AnimalEntity(
                id = "luna_456", name = "Luna", type = "Gato", status = "ENCONTRADO",
                location = "Las Condes", description = "Siamés con collar rojo."
            )
        )
        upsertAll(demo)
    }
}
