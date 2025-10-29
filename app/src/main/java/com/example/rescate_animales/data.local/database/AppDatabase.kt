package com.example.rescate_animales.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rescate_animales.data.local.dao.AnimalDao
import com.example.rescate_animales.data.local.dao.NotificationDao
import com.example.rescate_animales.data.local.dao.UserDao
import com.example.rescate_animales.data.local.entity.AnimalEntity
import com.example.rescate_animales.data.local.entity.NotificationEntity
import com.example.rescate_animales.data.local.entity.UserEntity

@Database(
    entities = [AnimalEntity::class, UserEntity::class, NotificationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun userDao(): UserDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rescate_animales.db"
                ).build().also { INSTANCE = it }
            }
    }
}
