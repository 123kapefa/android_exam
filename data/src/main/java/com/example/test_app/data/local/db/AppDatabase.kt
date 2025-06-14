package com.example.test_app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_app.data.local.dao.AchievementDao
import com.example.test_app.data.local.dao.PlayerDao
import com.example.test_app.data.local.entity.AchievementEntity
import com.example.test_app.data.local.entity.PlayerEntity

@Database(entities = [AchievementEntity::class, PlayerEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
    abstract fun playerDao(): PlayerDao
}