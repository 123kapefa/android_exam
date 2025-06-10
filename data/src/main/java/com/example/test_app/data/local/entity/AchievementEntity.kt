package com.example.test_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievement")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val isHidden: Boolean,
    val iconUrl: String,
    val iconGrayUrl: String,
    val isUnlocked: Boolean,
    val unlockTime: Long?
)