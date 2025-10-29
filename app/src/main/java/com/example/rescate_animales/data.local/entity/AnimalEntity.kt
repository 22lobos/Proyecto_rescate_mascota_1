package com.example.rescate_animales.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class AnimalEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val status: String,
    val location: String?,
    val description: String?
)
